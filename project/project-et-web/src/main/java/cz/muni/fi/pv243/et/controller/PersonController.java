/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.data.*;
import cz.muni.fi.pv243.et.model.*;
import org.hibernate.Session;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author rsmeral
 */
@Model
public class PersonController {

    @Inject
    private EntityManager em;

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

    public String createPersons() {
        
        Person person = new Person();

        person.setFirstName("Test");
        person.setLastName("Test");
        person.setEmail("test2@test.com");
        person.setBankAccount("123456789");

        pd.create(person);


        Person personNew = new Person();
        personNew.setFirstName("Test");
        personNew.setLastName("Test");
        personNew.setEmail("test@test.com");
        personNew.setBankAccount("123456789");

        pd.create(personNew);
        System.out.println("createPersons");

        // PURPOSES
        Purpose purp = new Purpose();
        purp.setDescription("Blabol");
        purp.setName("TestPurp");
        purposeRepo.create(purp);

        Purpose purp2 = new Purpose();
        purp2.setDescription("Blabol");
        purp2.setName("TestPurp");
        purposeRepo.create(purp2);


        // RECEIPTS
        Receipt rec = new Receipt();
        Date date = new Date();
        System.out.println("Date-time=" + date.getTime());
        rec.setImportDate(date);
        rec.setImportedBy(person);
        receiptRepo.create(rec);


        // REPORTS
        ExpenseReport report = new ExpenseReport();
        report.setSubmitter(personNew);
        report.setLastSubmittedDate(new Date(113, 0, 0) );
//        report.setApprovedDate(new Date(System.currentTimeMillis() + 1000000000));
        report.setStatus(ReportStatus.OPEN);
        expenseRepo.create(report);

        // PAYMENTS
        Payment payment = new Payment();
        payment.setPurpose(purp);
        payment.setReport(report);
        payment.setReceipt(rec);

        payment.setCurrency("$");
        payment.setValue(BigDecimal.valueOf(150));
        payment.setDate(new Date(System.currentTimeMillis()) );

        System.out.println("Payment set");
        paymentRepo.create(payment);

        System.out.println("Persisted");


        Session session = (Session) em.getDelegate();

        System.out.println("=============\n\n\n\n\n===============");
        System.out.println(session.createQuery("SELECT r from Receipt r where r.importedBy.email like 'test2%' ").list() );

        System.out.println("=============\n\n\n\n\n===============");
        return "created";
    }

    public Collection<Object> getPersons() {
//        System.out.println(plp.findAll());
        System.out.println("getPersons");
//        return pd.findAll();

        Collection<Object> entities = new ArrayList<Object>();

        //entities.add(plp.findByEmail("test2@test.com"));
        entities.add(plp.findAll());
        System.out.println("Person=" + entities);
        System.out.println("Purpose=" + purposeList.get(1L));

        System.out.println("All purposes " + purposeList.getAll());
        entities.add(purposeList.getAll());


        System.out.println("\nReceipt=" + receiptList.getAllReceipts() + "\n");
        entities.add(receiptList.getAllReceipts());


        // TESTING Remove entities :-)
        /*
        for (Person p : plp.findAll()) {
            System.out.println("\n Removing" + p);
            pd.remove(p);
        }

        System.out.println("Deleted persons");
        for (Purpose p : purposeList.getAll()) {
            System.out.println("\n Removing" + p);
            purposeRepo.remove(p.getId());
        }

        System.out.println("Deleted purposes");
        */


        //System.out.println("Payments=" + paymentListProducer.getAllPayments());

        return entities;
    }
}
