package cz.muni.fi.pv243.et.service;

import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Person;

import java.util.Collection;

public interface PaymentService {
    void save(Payment payment);
    void remove(Payment payment);
    Payment get(Long id);
    Collection<Payment> findAll();
    Collection<Payment> findByExpenseReport(ExpenseReport report);
    Collection<Payment> findByPerson(Person person);
}
