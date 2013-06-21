package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.PurposeListProducer;
import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Purpose;
import org.hibernate.Session;
import org.jboss.ejb3.annotation.Clustered;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;

@Clustered
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
    public Purpose getForPayment(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("payment is null");
        }
        return (Purpose) session.createQuery("SELECT payment.purpose FROM Payment payment WHERE payment.id = :id")
                .setParameter("id", payment.getId()).uniqueResult();
    }

    @Override
    public Collection<Purpose> getAll() {
        return session.createQuery("SELECT purpose FROM Purpose purpose").list();
    }
}
