package cz.muni.fi.pv243.et.service.impl;

import cz.muni.fi.pv243.et.data.MoneyTransferListProducer;
import cz.muni.fi.pv243.et.data.MoneyTransferRepository;
import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.MoneyTransfer;
import cz.muni.fi.pv243.et.security.annotation.Authenticated;
import cz.muni.fi.pv243.et.service.MoneyTransferService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Date;

@Stateless
@Authenticated
public class MoneyTransferServiceImpl implements MoneyTransferService {

    @Inject
    private MoneyTransferRepository repository;

    @Inject
    private MoneyTransferListProducer listProducer;

    @Override
    public void save(MoneyTransfer moneyTransfer) {
        if (moneyTransfer == null) {
            throw new IllegalArgumentException();
        }

        if (moneyTransfer.getId() == null) {
            repository.create(moneyTransfer);
        } else {
            repository.update(moneyTransfer);
        }
    }

    @Override
    public void remove(MoneyTransfer moneyTransfer) {
        if (moneyTransfer == null) {
            throw new IllegalArgumentException();
        }

        repository.remove(moneyTransfer);
    }

    @Override
    public MoneyTransfer get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }

        return listProducer.get(id);
    }

    @Override
    public Collection<MoneyTransfer> findAll() {
        return listProducer.getAll();
    }

    @Override
    public Collection<MoneyTransfer> findForReport(ExpenseReport report) {
        if (report == null) {
            throw new IllegalArgumentException();
        }

        return listProducer.get(report);
    }

    @Override
    public Collection<MoneyTransfer> findBetweenDates(Date from, Date to) {
        if (from == null && to == null) {
            throw new IllegalArgumentException();
        }

        return listProducer.getAllBetweenDates(from, to);
    }
}
