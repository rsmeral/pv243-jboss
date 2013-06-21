package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.ReceiptRepository;
import cz.muni.fi.pv243.et.model.Receipt;
import org.jboss.ejb3.annotation.Clustered;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Clustered
@Stateless
public class ReceiptRepositoryImpl implements ReceiptRepository {

    @Inject
    private EntityManager em;

    @Override
    public void create(Receipt receipt) {
        if (receipt == null) {
            throw new IllegalArgumentException("receipt is null");
        }
        if (receipt.getId() != null) {
            throw new IllegalArgumentException("receipt.id is not null");
        }
        em.persist(receipt);
    }

    @Override
    public void update(Receipt receipt) {
        if (receipt == null) {
            throw new IllegalArgumentException("receipt is null");
        }
        if (receipt.getId() == null) {
            throw new IllegalArgumentException("receipt.id is null");
        }
        em.merge(receipt);
    }

    @Override
    public void remove(Receipt receipt) {
        if (receipt == null) {
            throw new IllegalArgumentException("receipt is null");
        }
        if (receipt.getId() == null) {
            throw new IllegalArgumentException("receipt.id is null -> not persisted");
        }
        em.remove(em.find(Receipt.class, receipt.getId()));
    }
}
