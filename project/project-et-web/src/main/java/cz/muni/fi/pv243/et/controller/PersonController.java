/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.data.*;
import cz.muni.fi.pv243.et.model.*;

import java.util.ArrayList;
import java.util.Collection;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 * @author rsmeral
 */
@Model
public class PersonController {

    @Inject
    private PersonRepository pd;

    @Inject
    private PersonListProducer plp;

//    @Inject
//    private PaymentRepository paymentRepo;
//
//    @Inject
//    private PaymentListProducer paymentListProducer;

    @Inject
    private PurposeRepository purposeRepo;

    @Inject
    private PurposeListProducer purposeList;

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


        Purpose purp = new Purpose();
        purp.setDescription("Blabol");
        purp.setName("TestPurp");
        purposeRepo.create(purp);



//
//        // create test payments entities
//        Payment payment = new Payment();

//
//        ExpenseReport report = new ExpenseReport();
//        report.setSubmitter(personNew);
//        report.setId(2L);
//
//        Receipt rec = new Receipt();
//        rec.setId(1L);
//        rec.setImportDate(DateTime.now());
//
//        System.out.println("Created Purpose");
//        payment.setPurpose(purp);
//        payment.setReport(report);
//        payment.setReceipt(rec);
//
//        payment.setCurrency("$");
//        payment.setValue(BigDecimal.valueOf(150));
//        payment.setDate(DateTime.now());
//
//        System.out.println("Payment set");
//        paymentRepo.create(payment);

        System.out.println("Persisted");

        return "created";
    }

    public Collection<Object> getPersons() {
        System.out.println(plp.findAll());

        System.out.println("getPersons");
//        return pd.findAll();
        Collection<Object> people = new ArrayList<Object>();
        people.add(plp.findByEmail("test2@test.com"));
        System.out.println("Person=" + people);
        System.out.println("Purpose=" + purposeList.get(1L));

        people.add(purposeList.getAll());
        //System.out.println("Payments=" + paymentListProducer.getAllPayments());

        return people;
    }
}
