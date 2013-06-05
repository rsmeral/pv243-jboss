package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.data.*;
import cz.muni.fi.pv243.et.model.*;
import cz.muni.fi.pv243.et.model.Currency;
import cz.muni.fi.pv243.et.security.NewUser;
import org.apache.deltaspike.core.api.exception.control.event.ExceptionToCatchEvent;
import org.picketlink.idm.IdentityManager;

import java.math.BigDecimal;
import java.util.*;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 * @author rsmeral
 */
@Model
public class TestController {

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

    public String initialize() {
        HashSet<Role> roles = new HashSet<Role>();

        roles.add(Role.APPLICANT);
        Person personTom = createPerson("Tomas", "Lukasovic", "test@test.cz", "123456789", roles);

//        // create user
//        NewUser newUser = new NewUser();
//        newUser.setEmail("test@test.cz");
//        newUser.setFirstName("Tomas");
//        newUser.setLastName("Lukasovic");
//        newUser.setPassword("asd");
//        newUser.setPasswordConfirmation("asd");
//        newUser.setUserName("asd");
//        //im.add(newUser);

        roles.clear();
        roles.add(Role.APPLICANT);
        roles.add(Role.APPROVER);
        Person personJana = createPerson("Jana", "Nova", "nova.jana@test.cz", "789313244", roles);


        System.out.println("created Persons");

        // PURPOSES
        Purpose purp1 = createPurpose("TestPurp", "Purpose 2 Desctipriton");
        Purpose purp2 = createPurpose("Purpose 2", "Purpose tesxt asdasdsa");


        // RECEIPTS
        Receipt rec = createReceipt(personTom, new Date(System.currentTimeMillis() - 100000), "Platba za vlak a autobus");


        // REPORTS
        ExpenseReport report =
                createExpenseReport("Berlin 2013 Bienale", personTom, personJana, ReportStatus.OPEN, "Visit of some stupid Conference" );
//        report.setApprovedDate(new Date(System.currentTimeMillis() + 1000000000));
        ExpenseReport report2 =
                createExpenseReport("Munich 2012 FUD Conference", personTom, null, ReportStatus.APPROVED, "Conference in Munich" );

        // PAYMENTS
        Payment payment = createPayment(report, purp1, rec, BigDecimal.valueOf(1500), Currency.EUR);
        Payment payment2 = createPayment(report2, purp2, rec, BigDecimal.valueOf(2345), Currency.USD);


        return "created";
    }


    public Collection<Object> getPersons() {

        Collection<Object> entities = new ArrayList<Object>();

        //entities.add(plp.findByEmail("test2@test.com"));
        entities.add(expenseList.getAll());
//        entities.add(plp.findAll());
        System.out.println("Person=" + entities);
        System.out.println("Purpose=" + purposeList.get(1L));

        System.out.println("All purposes " + purposeList.getAll());
//        entities.add(purposeList.getAll());


        System.out.println("\nReceipt=" + receiptList.getAllReceipts() + "\n");

        return entities;
    }

    private Person createPerson(String firstName, String lastName, String email, String bankAccount, Set roles) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setEmail(email);
        person.setBankAccount(bankAccount);
        person.setRoles(roles);
        pd.create(person);

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
}
