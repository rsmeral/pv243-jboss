package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.PaymentListProducer;
import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Person;
import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.jboss.ejb3.annotation.Clustered;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Clustered
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
        FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
        QueryBuilder qb = ftem.getSearchFactory().buildQueryBuilder().forEntity(Payment.class).get();
        Query query = qb.keyword().onField("report.submitter.id").matching(person.getId()).createQuery();

        FullTextQuery fullTextQuery = ftem.createFullTextQuery(query, Payment.class);
        fullTextQuery.initializeObjectsWith(ObjectLookupMethod.SKIP, DatabaseRetrievalMethod.FIND_BY_ID);

        return fullTextQuery.getResultList();
//        return session.createQuery("SELECT payment FROM Payment payment WHERE payment.report.submitter.id = :personId")
//                .setParameter("personId", person.getId()).list();
    }

    @Override
    public List<Payment> getPaymentsBetweenDates(Date fromDate, Date toDate) {
        return session.createQuery("SELECT payment FROM Payment payment WHERE :fromDate <= payment.date AND payment.date <= :toDate")
                .setParameter("fromDate", fromDate).setParameter("toDate", toDate).list();
    }

    @Override
    public List<Payment> get(ExpenseReport report) {
        FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
        QueryBuilder qb = ftem.getSearchFactory().buildQueryBuilder().forEntity(Payment.class).get();
        Query query = qb.keyword().onField("report.id").matching(report.getId()).createQuery();

        FullTextQuery fullTextQuery = ftem.createFullTextQuery(query, Payment.class);
        fullTextQuery.initializeObjectsWith(ObjectLookupMethod.SKIP, DatabaseRetrievalMethod.FIND_BY_ID);

        return fullTextQuery.getResultList();
//        return session.createQuery("SELECT payment FROM Payment payment WHERE payment.report.id = :reportId")
//                .setParameter("reportId", report.getId()).list();
    }


}
