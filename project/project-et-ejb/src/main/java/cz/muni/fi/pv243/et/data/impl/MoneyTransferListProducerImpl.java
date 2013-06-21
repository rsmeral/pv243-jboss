package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.MoneyTransferListProducer;
import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.MoneyTransfer;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Clustered
@Stateless
public class MoneyTransferListProducerImpl implements MoneyTransferListProducer, Serializable {

    @Inject
    private EntityManager em;

    @Inject
    private Session session;

    @Override
    public MoneyTransfer get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        return em.find(MoneyTransfer.class, id);
    }

    /**
     * Return all associated money transfers with given expense report.
     *
     * @param expenseReport to look for in money transfers
     * @return collection of moneyTransfers associated with expenseReport or null.
     */
    @Override
    public Collection<MoneyTransfer> get(ExpenseReport expenseReport) {
        if (expenseReport == null) {
            throw new IllegalArgumentException("expenseReport is null");
        }

        FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
        QueryBuilder queryBuilder = ftem.getSearchFactory().buildQueryBuilder().forEntity(MoneyTransfer.class).get();
        Query query = queryBuilder.keyword().onField("report.id").matching(expenseReport.getId()).createQuery();

        FullTextQuery fullTextQuery = ftem.createFullTextQuery(query, MoneyTransfer.class);
        fullTextQuery.initializeObjectsWith(ObjectLookupMethod.SKIP, DatabaseRetrievalMethod.FIND_BY_ID);

        return fullTextQuery.getResultList();
    }

    /**
     * Single parameter could be null, but not both.
     *
     * @param fromDate - if null, list all moneyTransfers toDate
     * @param toDate   - if null, list all fromDate to now
     * @return list of MoneyTransfers from given time period.
     */
    @Override
    public List<MoneyTransfer> getAllBetweenDates(Date fromDate, Date toDate) {
        if (fromDate == null && toDate == null) {
            throw new IllegalArgumentException("fromDate and toDate are both null");
        }

        List<MoneyTransfer> result;
        // list all entries to toDate  date_interval<0, toDate>
        if (fromDate == null) {
            result = session.createQuery("SELECT mt FROM MoneyTransfer mt WHERE mt.date <= :toDate ")
                    .setParameter("toDate", toDate).list();
        } else if (toDate == null) {
            result = session.createQuery("SELECT mt FROM MoneyTransfer mt WHERE mt.date >= :fromDate ")
                    .setParameter("fromDate", fromDate).list();
        } else {
            result = session.createQuery("SELECT mt FROM MoneyTransfer mt WHERE :fromDate <= mt.date AND mt.date < :toDate ")
                    .setParameter("toDate", toDate).setParameter("fromDate", fromDate).list();
        }
        return result;
    }


    @Override
    public Collection<MoneyTransfer> getAll() {
        return session.createQuery("SELECT mt from MoneyTransfer mt").list();
    }
}
