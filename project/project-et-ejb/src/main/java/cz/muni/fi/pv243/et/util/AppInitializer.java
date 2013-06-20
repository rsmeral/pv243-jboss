package cz.muni.fi.pv243.et.util;

import cz.muni.fi.pv243.et.data.ExpenseReportListProducer;
import cz.muni.fi.pv243.et.data.ExpenseReportRepository;
import cz.muni.fi.pv243.et.data.MoneyTransferListProducer;
import cz.muni.fi.pv243.et.data.MoneyTransferRepository;
import cz.muni.fi.pv243.et.data.PaymentListProducer;
import cz.muni.fi.pv243.et.data.PaymentRepository;
import cz.muni.fi.pv243.et.data.PersonListProducer;
import cz.muni.fi.pv243.et.data.PersonRepository;
import cz.muni.fi.pv243.et.data.PurposeListProducer;
import cz.muni.fi.pv243.et.data.PurposeRepository;
import cz.muni.fi.pv243.et.data.ReceiptListProducer;
import cz.muni.fi.pv243.et.data.ReceiptRepository;
import cz.muni.fi.pv243.et.model.Currency;
import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.MoneyTransfer;
import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.PersonRole;
import cz.muni.fi.pv243.et.model.Purpose;
import cz.muni.fi.pv243.et.model.Receipt;
import cz.muni.fi.pv243.et.model.ReportStatus;
import cz.muni.fi.pv243.et.model.UserModel;
import cz.muni.fi.pv243.et.security.EventLog;
import cz.muni.fi.pv243.et.security.UserManager;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.credential.internal.Password;
import org.picketlink.idm.model.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.event.Event;
import javax.inject.Named;
import org.apache.deltaspike.core.api.exception.control.event.ExceptionToCatchEvent;

@Singleton
@Startup
@Named
public class AppInitializer {

    @Inject
    private Event<ExceptionToCatchEvent> exceptionEvent;

    @Inject
    private PersonRepository pd;

    @Inject
    private PersonListProducer plp;

    @Inject
    private PaymentRepository paymentRepo;

    @Inject
    private PaymentListProducer paymentListProducer;

    @Inject
    private PurposeRepository purposeRepo;

    @Inject
    private PurposeListProducer purposeList;

    @Inject
    private ReceiptRepository receiptRepo;

    @Inject
    private ReceiptListProducer receiptList;

    @Inject
    private ExpenseReportRepository expenseRepo;

    @Inject
    private ExpenseReportListProducer expenseList;

    @Inject
    private MoneyTransferRepository moneyRepo;

    @Inject
    private MoneyTransferListProducer moneyList;

    @Inject
    private UserManager userManager;

    public String initialize() {

        List<PersonRole> roles = new ArrayList<PersonRole>();
        roles.add(PersonRole.APPLICANT);

        Person personApplicant = createPerson("Alice", "Applicantovic", "test@test.cz", "123456789", roles);
        roles.add(PersonRole.VERIFIER);
        Person personBoth = createPerson("Bob", "Both", "nova.jana@test.cz", "789313244", roles);
        roles.clear();
        roles.add(PersonRole.VERIFIER);
        Person personApprover = createPerson("Vera", "Approverova", "herim@ona.cz", "120313244", roles);

        System.out.println("created Persons");

        // PURPOSES
        Purpose purp1 = createPurpose("TestPurp", "Purpose 2 Desctipriton");
        Purpose purp2 = createPurpose("Purpose 2", "Purpose of not writing nice Java code");
        Purpose purp3 = createPurpose("Purpose 3", "Arquillian not testing purpose - give us some money bicth!");


        // RECEIPTS
        Receipt rec = createReceipt(personApplicant, new Date(System.currentTimeMillis() - 100000), "Platba za vlak a autobus NULL");
        Receipt rec2 = createReceipt(personBoth, new Date(System.currentTimeMillis() - 20000), "Way there and back NULL");


        // REPORTS
        ExpenseReport report =
                createExpenseReport("Berlin 2013 Bienale", personApplicant, personBoth, ReportStatus.OPEN, "Visit of some stupid Conference" );
//        report.setApprovedDate(new Date(System.currentTimeMillis() + 1000000000));
        ExpenseReport report2 =
                createExpenseReport("Munich 2012 FUD Conference", personApplicant, null, ReportStatus.APPROVED, "Conference in Munich" );


        ExpenseReport report3 =
                createExpenseReport("Non Testing in Arquillian 2012", personBoth, personApprover, ReportStatus.REJECTED, "Why are we not testing in Arquillian?" );

        // MONEY TRANSFERS
        MoneyTransfer mt = createMoneyTransfer(personApprover, report2, BigDecimal.valueOf(2400), Currency.CZK, new Date(System.currentTimeMillis() + 20));
        MoneyTransfer mt2 = createMoneyTransfer(personApprover, report3, BigDecimal.valueOf(200), Currency.CZK, new Date(System.currentTimeMillis() - 15000));
        MoneyTransfer mt3 = createMoneyTransfer(personApprover, report3, BigDecimal.valueOf(100), Currency.CZK, new Date(System.currentTimeMillis() - 14000));

        // PAYMENTS
        Payment payment = createPayment(report, purp1, rec, BigDecimal.valueOf(1500), Currency.CZK);
        Payment payment2 = createPayment(report2, purp2, rec, BigDecimal.valueOf(2345), Currency.CZK);
        Payment payment3 = createPayment(report3, purp3, rec2, BigDecimal.valueOf(300), Currency.CZK);
        Payment payment4 = createPayment(report3, purp3, rec2, BigDecimal.valueOf(100), Currency.CZK);

        return "created";
    }



    private Person createPerson(String firstName, String lastName, String email, String bankAccount, List<PersonRole> roles) {
        UserModel model = new UserModel();
        model.setFirstName(firstName);
        model.setLastName(lastName);
        model.setEmail(email);
        model.setBankAccount(bankAccount);
        model.setUserName(firstName.toLowerCase());
        userManager.add(model);

        // firstname.tolowercase == username == password
        userManager.changePassword(model.getUserName(), model.getUserName());

        for (PersonRole role : roles) {
            System.out.println(model.getUserName() + " role=" + role);
            userManager.grantRole(model.getUserName(), role);
        }


        Person person = new Person();
        person.setId(model.getId());
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setEmail(email);
        person.setBankAccount(bankAccount);

        return person;
    }

    private Purpose createPurpose(String name, String description) {
        Purpose purpose = new Purpose();
        purpose.setDescription(description);
        purpose.setName(name);
        purposeRepo.create(purpose);

        return purpose;
    }

    private Receipt createReceipt(Person importedBy, Date date, String documentName) {
        Receipt receipt = new Receipt();
        receipt.setImportDate(date);
        receipt.setImportedBy(importedBy);
        receipt.setDocumentName(documentName);
//        receipt.setDocument();
        receiptRepo.create(receipt);

        return receipt;
    }


    private ExpenseReport createExpenseReport(String name, Person submitter, Person verifier, ReportStatus status, String description) {
        ExpenseReport report = new ExpenseReport();
        report.setName(name);
        report.setSubmitter(submitter);
        report.setVerifier(verifier);
        report.setStatus(status);
        report.setDescription(description);
        report.setLastSubmittedDate(new Date(System.currentTimeMillis()));
//        report.setApprovedDate();
//        report.setPayments();           -- REMOVE IT ?? How do we track this thing? We set payments to reports (?)
//        report.setMoneyTransfers();
        expenseRepo.create(report);

        return report;
    }

    private Payment createPayment(ExpenseReport report, Purpose purpose, Receipt receipt, BigDecimal value, Currency currency) {
        Payment payment = new Payment();
        payment.setPurpose(purpose);
        payment.setReport(report);
        payment.setReceipt(receipt);
        payment.setCurrency(currency);
        payment.setValue(value);
        payment.setDate(new Date(System.currentTimeMillis() + 10000));
        paymentRepo.create(payment);

        return payment;
    }

    private MoneyTransfer createMoneyTransfer(Person creator, ExpenseReport report, BigDecimal value, Currency currency, Date date) {
        MoneyTransfer mt = new MoneyTransfer();
        mt.setReport(report);
        mt.setCreator(creator);
        mt.setValue(value);
        mt.setCurrency(currency);
        mt.setDate(date);
        moneyRepo.create(mt);

        return mt;
    }
}
