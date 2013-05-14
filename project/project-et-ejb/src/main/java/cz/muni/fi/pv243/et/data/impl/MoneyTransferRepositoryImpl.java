package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.MoneyTransferRepository;
import cz.muni.fi.pv243.et.model.MoneyTransfer;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class MoneyTransferRepositoryImpl implements MoneyTransferRepository {

    @Inject
    private EntityManager em;

    @Override
    public void create(MoneyTransfer moneyTransfer) {
        if (moneyTransfer == null) {
            throw new IllegalArgumentException("moneyTransfer is null");
        }
        if (moneyTransfer.getId() != null) {
            throw new IllegalArgumentException("moneyTransfer.id is not null");
        }
        em.persist(moneyTransfer);
    }

    @Override
    public void update(MoneyTransfer moneyTransfer) {
        if (moneyTransfer == null) {
            throw new IllegalArgumentException("moneyTransfer is null");
        }
        if (moneyTransfer.getId() == null) {
            throw new IllegalArgumentException("moneyTransfer.id is null");
        }
        em.merge(moneyTransfer);
    }

    @Override
    public void remove(MoneyTransfer moneyTransfer) {
        if (moneyTransfer == null) {
            throw new IllegalArgumentException("moneyTransfer is null");
        }
        if (moneyTransfer.getId() == null) {
            throw new IllegalArgumentException("moneyTransfer.id is null");
        }
        em.remove(moneyTransfer);
    }
}
