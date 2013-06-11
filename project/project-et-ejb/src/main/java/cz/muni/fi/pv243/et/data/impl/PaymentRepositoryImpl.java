package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.PaymentRepository;
import cz.muni.fi.pv243.et.model.Payment;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;


@Stateless
public class PaymentRepositoryImpl implements PaymentRepository {

    @Inject
    private EntityManager em;

    @Override
    public void create(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("payment is null");
        }
        if (payment.getId() != null) {
            throw new IllegalArgumentException("payment.id is not null");
        }
        em.persist(payment);
    }

    @Override
    public void update(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("payment is null");
        }
        if (payment.getId() == null) {
            throw new IllegalArgumentException("payment.id is null - not persisted");
        }
        em.merge(payment);
//        em.flush();
    }

    @Override
    public void remove(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("payment is null");
        }
        if (payment.getId() == null) {
            throw new IllegalArgumentException("payment.id is null - not persisted");
        }
//        em.remove(em.find(Payment.class, payment.getId()));
        em.remove(payment);
    }
}
