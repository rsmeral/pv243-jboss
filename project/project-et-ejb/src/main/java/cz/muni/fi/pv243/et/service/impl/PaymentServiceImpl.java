package cz.muni.fi.pv243.et.service.impl;

import cz.muni.fi.pv243.et.data.PaymentListProducer;
import cz.muni.fi.pv243.et.data.PaymentRepository;
import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.security.annotation.Authenticated;
import cz.muni.fi.pv243.et.service.PaymentService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

@Stateless
@Authenticated
public class PaymentServiceImpl implements PaymentService {
    
    @Inject
    private PaymentRepository paymentRepository;
    
    @Inject
    private PaymentListProducer paymentListProducer;

    @Override
    public void save(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException();
        }
        if (payment.getId() == null) {
            paymentRepository.create(payment);
        } else {
            paymentRepository.update(payment);
        }
    }

    @Override
    public void remove(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException();
        }
        paymentRepository.remove(payment);
    }

    @Override
    public Payment get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        return paymentListProducer.getPayment(id);
    }

    @Override
    public Collection<Payment> findAll() {
        return paymentListProducer.getAllPayments();
    }

    @Override
    public Collection<Payment> findByExpenseReport(ExpenseReport report) {
        if (report == null) {
            throw new IllegalArgumentException();
        }
        return paymentListProducer.get(report);
    }

    @Override
    public Collection<Payment> findByPerson(Person person) {
        if (person == null) {
            throw new IllegalArgumentException();
        }
        return paymentListProducer.getAllPayments(person);
    }
}
