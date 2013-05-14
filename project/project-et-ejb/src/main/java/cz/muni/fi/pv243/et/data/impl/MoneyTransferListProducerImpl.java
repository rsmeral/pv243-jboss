package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.MoneyTransferListProducer;
import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.MoneyTransfer;
import org.hibernate.Session;
import org.joda.time.DateTime;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

@Stateless
public class MoneyTransferListProducerImpl implements MoneyTransferListProducer {

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

    @Override
    public Collection<MoneyTransfer> get(ExpenseReport expenseReport) {
       return null;
    }

    /**
     * Single parameter could be null, but not both.
     *
     * @param fromDate - if null, list all moneyTransfers toDate
     * @param toDate - if null, list all fromDate to now
     * @return list of MoneyTransfers from given time period.
     */
    @Override
    public List<MoneyTransfer> getAllBetweenDates(DateTime fromDate, DateTime toDate) {
        if (fromDate == null && toDate == null) {
            throw new IllegalArgumentException("fromDate and toDate are both null");
        }

        List<MoneyTransfer> result;
        // list all entries to toDate  date_interval<0, toDate>
        if (fromDate == null) {
            result = session.createQuery("SELECT mt FROM MoneyTransfer mt WHERE date < :toDate ")
                    .setParameter("toDate", toDate).list();
        } else if (toDate == null) {
            result = session.createQuery("SELECT mt FROM MoneyTransfer mt WHERE date > :fromDate ")
                    .setParameter("fromDate", fromDate).list();
        } else {
            result = session.createQuery("SELECT mt FROM MoneyTransfer mt WHERE :fromDate < date and date < :toDate ")
                    .setParameter("toDate", toDate).setParameter("fromDate", fromDate).list();
        }
        return result;
    }


    @Override
    public Collection<MoneyTransfer> getAll() {
        return session.createQuery("SELECT mt from MoneyTransfer mt").list();
    }
}
