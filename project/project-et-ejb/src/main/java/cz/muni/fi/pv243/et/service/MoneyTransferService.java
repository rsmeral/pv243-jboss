package cz.muni.fi.pv243.et.service;

import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.MoneyTransfer;

import java.util.Collection;
import java.util.Date;

public interface MoneyTransferService {
    void save(MoneyTransfer moneyTransfer);
    void remove(MoneyTransfer moneyTransfer);
    MoneyTransfer get(Long id);
    Collection<MoneyTransfer> findAll();
    Collection<MoneyTransfer> findForReport(ExpenseReport report);
    Collection<MoneyTransfer> findBetweenDates(Date from, Date to);
}
