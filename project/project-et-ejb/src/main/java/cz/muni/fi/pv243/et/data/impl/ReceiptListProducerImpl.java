package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.ReceiptListProducer;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.Receipt;
import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.TermTermination;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Stateless
public class ReceiptListProducerImpl implements ReceiptListProducer {

    @Inject
    private EntityManager em;

    @Inject
    private Session session;


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
        FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
        QueryBuilder queryBuilder = ftem.getSearchFactory().buildQueryBuilder().forEntity(Receipt.class).get();
        Query query = queryBuilder.keyword().onField("importedBy.id").matching(importedBy.getId()).createQuery();

        FullTextQuery fullTextQuery = ftem.createFullTextQuery(query, Receipt.class);
        return fullTextQuery.getResultList();
//
//        return session.createQuery("SELECT receipt FROM Receipt receipt WHERE receipt.importedBy.id = :personId")
//                .setParameter("personId", importedBy.getId()).list();
    }

    @Override
    public List<Receipt> getReceiptsFromDate(Date fromDate) {
        return session.createQuery("SELECT receipt FROM Receipt receipt WHERE receipt.importDate <= :fromDate")
                .setParameter("fromDate", fromDate).list();
    }

    @Override
    public Collection<Receipt> getAllReceipts() {
        return session.createQuery("SELECT receipt FROM Receipt receipt").list();
    }
}
