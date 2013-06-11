package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.ReceiptListProducer;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.Receipt;
import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Stateless
public class ReceiptListProducerImpl implements ReceiptListProducer, Serializable {

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
        return session.createQuery("SELECT receipt FROM Receipt receipt WHERE receipt.importedBy.id = :personId")
                .setParameter("personId", importedBy.getId()).list();
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
