package cz.muni.fi.pv243.et.test;

import cz.muni.fi.pv243.et.data.ExpenseReportListProducer;
import cz.muni.fi.pv243.et.data.PersonListProducer;
import cz.muni.fi.pv243.et.data.PurposeListProducer;
import cz.muni.fi.pv243.et.model.Currency;
import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.MoneyTransfer;
import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.PersonRole;
import cz.muni.fi.pv243.et.model.Purpose;
import cz.muni.fi.pv243.et.model.ReportStatus;
import cz.muni.fi.pv243.et.model.UserModel;
import cz.muni.fi.pv243.et.security.UserManager;
import cz.muni.fi.pv243.et.service.ExpenseReportService;
import cz.muni.fi.pv243.et.service.LoginService;
import cz.muni.fi.pv243.et.service.MoneyTransferService;
import cz.muni.fi.pv243.et.service.PaymentService;
import cz.muni.fi.pv243.et.service.PersonService;
import cz.muni.fi.pv243.et.service.PurposeService;
import cz.muni.fi.pv243.et.service.impl.ReportComputing;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.picketlink.Identity;
import org.picketlink.authentication.AuthenticationException;

@RunWith(Arquillian.class)
public class ExpenseTrackingTest {

    @Inject
    private LoginService loginService;

    @Inject
    private ExpenseReportService reportSvc;

    @Inject
    private Identity identity;

    @Inject
    private PersonService personSvc;

    @Inject
    private PurposeService purposeSvc;

    @Inject
    private MoneyTransferService mtSvc;

    @Inject
    private PaymentService paySvc;

    @Inject
    private UserManager usrMgr;

    @Inject
    private ReportComputing reportComputer;

    @Inject
    private ExpenseReportListProducer reportList;

    @Inject
    private PersonListProducer personList;

    @Inject
    private PurposeListProducer purpList;

    private static final String ADMIN = "admin";

    private static final String VERIFIER = "verifier";

    private static final String SUBMITTER = "submitter";

    @Deployment(testable = true)
    public static EnterpriseArchive deployment() {
        return Deployments.mainDeployment(ExpenseTrackingTest.class);
    }

    @Test
    @InSequence(1)
    public void testUserCreation() {
        loginAs(ADMIN);
        createUser(new UserModel("submitter", "Tester", "Submitter", "submitter@tester.com", "submitter", "123456789"), PersonRole.APPLICANT);
        createUser(new UserModel("verifier", "Tester", "Verifier", "verifier@tester.com", "verifier", "123456789"), PersonRole.VERIFIER);

        assertEquals(3, usrMgr.findAll().size()); // 2 + admin
    }

    @Test
    @InSequence(2)
    public void testCreateReport() {
        loginAs(SUBMITTER);

        ExpenseReport report = new ExpenseReport();
        report.setName("Report1");
        report.setDescription("Report1 desc");
        report.setSubmitter(getPersonForUser(SUBMITTER));
        report.setStatus(ReportStatus.OPEN);
        reportSvc.save(report);

        loginAs(VERIFIER);
        assertEquals(1, reportSvc.findAll().size());
        assertEquals(report, reportSvc.findAll().iterator().next());
    }

    @Test
    @InSequence(3)
    public void testPurposeSvc() {
        loginAs(ADMIN);

        Purpose purpose = new Purpose();
        purpose.setName("Purp1");
        purpose.setDescription("purpose 1");
        purposeSvc.save(purpose);
        Collection<Purpose> all = purposeSvc.findAll();
        assertEquals(1, all.size());
        assertEquals(purpose, all.iterator().next());
    }

    @Test
    @InSequence(4)
    public void testPaymentSvc() {
        loginAs(SUBMITTER);

        Payment payment = new Payment();
        payment.setPurpose(purpList.getAll().iterator().next());
        payment.setCurrency(Currency.CZK);
        payment.setDate(new Date());
        payment.setValue(BigDecimal.valueOf(100));
        payment.setReport(getOnlyReport());
        paySvc.save(payment);

        Collection<Payment> payments = paySvc.findByPerson(getPersonForUser(SUBMITTER));
        assertEquals(1, payments.size());
        assertEquals(payment, payments.iterator().next());
    }

    @Test
    @InSequence(5)
    public void testMoneyTransferSvc() {
        loginAs(VERIFIER);

        MoneyTransfer mt = new MoneyTransfer();
        mt.setCreator(getPersonForUser(VERIFIER));
        mt.setCurrency(Currency.CZK);
        mt.setDate(new Date());
        mt.setValue(BigDecimal.valueOf(300));
        mt.setReport(getOnlyReport());
        mtSvc.save(mt);

        Collection<MoneyTransfer> mts = mtSvc.findForReport(getOnlyReport());
        assertEquals(1, mts.size());
        assertEquals(mt, mts.iterator().next());
    }

    @Test
    @InSequence(6)
    public void testReportComputing() {
        loginAs(SUBMITTER);

        BigDecimal totalValue = reportComputer.getTotalValue(getOnlyReport());
        assertEquals(BigDecimal.valueOf(200), totalValue);
    }

    @Test
    @InSequence(7)
    public void testSubmitReport() {
        loginAs(SUBMITTER);

        ExpenseReport report = getOnlyReport();
        reportSvc.submit(report);
        assertEquals(ReportStatus.SUBMITTED, report.getStatus());
    }

    @Test
    @InSequence(8)
    public void testFindUnassignedReports() {
        loginAs(VERIFIER);

        ExpenseReport report = getOnlyReport();
        Collection<ExpenseReport> unassigned = reportSvc.findWithNoVerifierAssigned();
        assertEquals(1, unassigned.size());
        assertEquals(report, unassigned.iterator().next());
    }

    @Test
    @InSequence(9)
    public void testClaimReport() {
        loginAs(VERIFIER);

        ExpenseReport report = getOnlyReport();
        Person verifierPerson = getPersonForUser(VERIFIER);
        reportSvc.claim(report, verifierPerson);
        Collection<ExpenseReport> reportsForVerifier = reportSvc.findForVerifier(verifierPerson);
        assertTrue(reportsForVerifier.contains(report));
    }

    @Test
    @InSequence(10)
    public void testApproveReport() {
        loginAs(VERIFIER);

        ExpenseReport report = getOnlyReport();
        reportSvc.approve(report);
        
        Collection<ExpenseReport> allApproved = reportSvc.findByStatus(ReportStatus.APPROVED);
        Collection<ExpenseReport> forVerifierWithStatusApproved = reportSvc.findForVerifierWithStatus(getPersonForUser(VERIFIER), ReportStatus.APPROVED);
        assertEquals(allApproved, forVerifierWithStatusApproved);
    }

    @Test
    @InSequence(11)
    public void testSendMoney() {
        loginAs(VERIFIER);

        ExpenseReport report = getOnlyReport();
        reportSvc.sendMoney(report);
        assertEquals(ReportStatus.SETTLED, report.getStatus());
        assertEquals(0, reportSvc.findForSubmitterWithStatus(getPersonForUser(SUBMITTER), ReportStatus.OPEN).size());
    }

    private void loginAs(String username) {
        try {
            if (identity.isLoggedIn()) {
                identity.logout();
            }
            loginService.login(username, username);
        } catch (AuthenticationException ex) {
            fail(ex.getMessage());
        }
    }

    private void createUser(UserModel userModel, PersonRole... roles) {
        usrMgr.add(userModel);
        for (PersonRole role : roles) {
            usrMgr.grantRole(userModel.getUserName(), role);
        }
        usrMgr.changePassword(userModel.getUserName(), userModel.getUserName());
    }

    private Person getPersonForUser(String username) {
        UserModel usr = usrMgr.get(username);
        return personList.getPerson(usr.getId());
    }

    private ExpenseReport getOnlyReport() {
        Collection<ExpenseReport> all = reportList.getAll();
        assertFalse(all.isEmpty());
        return all.iterator().next();
    }
}
