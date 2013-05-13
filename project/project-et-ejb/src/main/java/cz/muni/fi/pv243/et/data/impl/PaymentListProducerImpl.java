package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.PaymentListProducer;
import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.Transaction;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.hibernate.search.query.dsl.QueryBuilder;
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

    FullTextEntityManager ftem = Search.getFullTextEntityManager(em);


    private QueryBuilder getQueryBuilder(FullTextEntityManager ftem, Class clazz) {
        return ftem.getSearchFactory().buildQueryBuilder().forEntity(clazz).get();
    }


    @Override
    public Payment getPayment(Long id) {
        QueryBuilder qb = getQueryBuilder(ftem, Payment.class);
        Query lucene = qb.keyword().onField("id").matching(id).createQuery();

        // nothing wrong to return null
        return (Payment) ftem.createFullTextQuery(lucene).getSingleResult();
    }

    @Override
    public Payment getPayment(Transaction transaction) {
        QueryBuilder qb = getQueryBuilder(ftem, Payment.class);
        Query lucene = qb.keyword().onField("transaction").matching(transaction).createQuery();

        return (Payment) ftem.createFullTextQuery(lucene).getSingleResult();
    }

    @Override
    public Collection<Payment> getAllPayments() {
        QueryBuilder qb = getQueryBuilder(ftem, Payment.class);
        Query lucene = qb.all().createQuery();

        return (List<Payment>) ftem.createFullTextQuery(lucene).getSingleResult();
    }

    @Override
    public Collection<Payment> getAllPayments(Person person) {
        QueryBuilder qb = getQueryBuilder(ftem, Payment.class);
        Query lucene = qb.keyword().onField("person").matching(person).createQuery();

        return (List<Payment>) ftem.createFullTextQuery(lucene)
                .initializeObjectsWith(ObjectLookupMethod.SECOND_LEVEL_CACHE, DatabaseRetrievalMethod.FIND_BY_ID)
                .getSingleResult();
    }

    @Override
    public List<Payment> getPaymentsBetweenDates(DateTime fromDate, DateTime toDate) {
        QueryBuilder qb = getQueryBuilder(ftem, Payment.class);
        // ?? Transaction.date or date
        Query lucene = qb.range().onField("Transaction.date").from(fromDate).to(toDate).createQuery();

        return (List<Payment>) ftem.createFullTextQuery(lucene)
                .initializeObjectsWith(ObjectLookupMethod.SECOND_LEVEL_CACHE, DatabaseRetrievalMethod.FIND_BY_ID)
                .getSingleResult();
    }
}
