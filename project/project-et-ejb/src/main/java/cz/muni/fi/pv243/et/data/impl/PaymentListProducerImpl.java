package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.PaymentListProducer;
import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Person;
import org.hibernate.Session;
import org.joda.time.DateTime;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

@Stateless
public class PaymentListProducerImpl implements PaymentListProducer {

    @Inject
    private EntityManager em;

    @Inject
    private Session session;

    @Override
    public Payment getPayment(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        return em.find(Payment.class, id);
    }

    @Override
    public Collection<Payment> getAllPayments() {
        return session.createQuery("SELECT payment FROM Payment payment").list();
    }

    @Override
    public Collection<Payment> getAllPayments(Person person) {
        return session.createQuery("SELECT payment FROM Payment payment WHERE payment.report.submitter.personId = :personId")
                .setParameter("personId", person.getPersonId()).list();
    }

    @Override
    public List<Payment> getPaymentsBetweenDates(DateTime fromDate, DateTime toDate) {
        return session.createQuery("SELECT payment FROM Payment payment WHERE :fromDate <= payment.date AND payment.date <= :toDate")
                .setParameter("fromDate", fromDate).setParameter("toDate", toDate).list();
    }
}
