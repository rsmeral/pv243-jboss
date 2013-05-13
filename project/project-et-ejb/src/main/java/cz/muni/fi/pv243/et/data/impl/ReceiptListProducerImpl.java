package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.HibernateSearchUtil;
import cz.muni.fi.pv243.et.data.ReceiptListProducer;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.Receipt;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
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
public class ReceiptListProducerImpl implements ReceiptListProducer {

    @Inject
    private EntityManager em;



    @Override
    public Receipt getReceipt(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        return em.find(Receipt.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public Collection<Receipt> getReceipts(Person importedBy) {
        final FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
        final QueryBuilder qb = HibernateSearchUtil.getQueryBuilder(ftem, Receipt.class);
        Query luceneQuery = qb.keyword().onField("person").matching(importedBy).createQuery();

        return ftem.createFullTextQuery(luceneQuery)
                .initializeObjectsWith(ObjectLookupMethod.SECOND_LEVEL_CACHE, DatabaseRetrievalMethod.FIND_BY_ID)
                .getResultList();
    }

    @Override
    public List<Receipt> getReceiptsFromDate(DateTime fromDate) {
        final FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
        final QueryBuilder qb = HibernateSearchUtil.getQueryBuilder(ftem, Receipt.class);
        Query luceneQuery = qb.range().onField("fromDate").from(fromDate).to(DateTime.now()).createQuery();

        return ftem.createFullTextQuery(luceneQuery)
                .initializeObjectsWith(ObjectLookupMethod.SECOND_LEVEL_CACHE, DatabaseRetrievalMethod.FIND_BY_ID)
                .getResultList();
    }

    @Override
    public Collection<Receipt> getAllReceipts() {
        final FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
        final QueryBuilder qb = HibernateSearchUtil.getQueryBuilder(ftem, Receipt.class);
        Query luceneQuery = qb.all().createQuery();

        return ftem.createFullTextQuery(luceneQuery)
                .initializeObjectsWith(ObjectLookupMethod.SECOND_LEVEL_CACHE, DatabaseRetrievalMethod.FIND_BY_ID)
                .getResultList();
    }
}
