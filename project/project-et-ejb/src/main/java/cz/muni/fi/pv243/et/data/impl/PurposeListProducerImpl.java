package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.HibernateSearchUtil;
import cz.muni.fi.pv243.et.data.PurposeListProducer;
import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Purpose;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.hibernate.search.query.dsl.QueryBuilder;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;

public class PurposeListProducerImpl implements PurposeListProducer {

    @Inject
    private EntityManager em;

    @Override
    public Purpose get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("purpose.id is null");
        }
        return em.find(Purpose.class, id);
    }

    @Override
    public Purpose get(Payment payment) {
        // use hibernate search !!
        if (payment == null) {
            throw new IllegalArgumentException("payment is null");
        }
        return em.find(Purpose.class, payment);
    }

    @Override
    public Collection<Purpose> getAll() {
        FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
        QueryBuilder qb = HibernateSearchUtil.getQueryBuilder(ftem, Purpose.class);

        Query luceneQuery = qb.all().createQuery();

        return ftem.createFullTextQuery(luceneQuery, Purpose.class)
                .initializeObjectsWith(ObjectLookupMethod.SECOND_LEVEL_CACHE, DatabaseRetrievalMethod.FIND_BY_ID)
                .getResultList();
    }
}
