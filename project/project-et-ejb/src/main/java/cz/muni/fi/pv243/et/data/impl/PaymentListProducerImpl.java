package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.PaymentListProducer;
import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Person;
import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Stateless
public class PaymentListProducerImpl implements PaymentListProducer, Serializable {

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
        return session.createQuery("SELECT payment FROM Payment payment WHERE payment.report.submitter.id = :personId")
                .setParameter("personId", person.getId()).list();
    }

    @Override
    public List<Payment> getPaymentsBetweenDates(Date fromDate, Date toDate) {
        return session.createQuery("SELECT payment FROM Payment payment WHERE :fromDate <= payment.date AND payment.date <= :toDate")
                .setParameter("fromDate", fromDate).setParameter("toDate", toDate).list();
    }

    @Override
    public List<Payment> get(ExpenseReport report) {
        return session.createQuery("SELECT payment FROM Payment payment WHERE payment.report.id = :reportId")
                .setParameter("reportId", report.getId()).list();
    }


}
