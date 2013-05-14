package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.PurposeListProducer;
import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Purpose;
import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;

@Stateless
public class PurposeListProducerImpl implements PurposeListProducer {

    @Inject
    private EntityManager em;

    @Inject
    private Session session;

    @Override
    public Purpose get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("purpose.id is null");
        }
        return em.find(Purpose.class, id);
    }

    @Override
    public Purpose get(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("payment is null");
        }
        return em.find(Purpose.class, payment);
    }

    @Override
    public Collection<Purpose> getAll() {
        return session.createQuery("SELECT purpose FROM Purpose purpose").list();
    }
}
