package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.PaymentRepository;
import cz.muni.fi.pv243.et.model.Payment;

import javax.inject.Inject;
import javax.persistence.EntityManager;


public class PaymentRepositoryImpl implements PaymentRepository {

    @Inject
    private EntityManager em;

    @Override
    public void createPayment(Payment payment) {
        em.persist(payment);
        em.flush();
    }

    @Override
    public void updatePayment(Payment payment) {
        em.merge(payment);
        em.flush();
    }

    @Override
    public void removePayment(Payment payment) {
        //em.remove(em.find(Payment.class, payment.getId()));
        em.remove(payment);
        em.flush();
    }
}
