package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.ReceiptListProducer;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.Receipt;
import org.hibernate.Session;
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
        return session.createQuery("SELECT receipt FROM Receipt receipt WHERE receipt.importedBy = :person")
                .setParameter("person", importedBy).list();
    }

    @Override
    public List<Receipt> getReceiptsFromDate(DateTime fromDate) {
        return session.createQuery("SELECT receipt FROM Receipt receipt WHERE receipt.importDate <= :fromDate")
                .setParameter("fromDate", fromDate).list();
    }

    @Override
    public Collection<Receipt> getAllReceipts() {
        return session.createQuery("SELECT receipt FROM Receipt receipt").list();
    }
}
