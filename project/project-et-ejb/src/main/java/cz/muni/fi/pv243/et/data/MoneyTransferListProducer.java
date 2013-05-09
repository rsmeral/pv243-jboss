package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.MoneyTransfer;
import cz.muni.fi.pv243.et.model.Transaction;
import org.joda.time.DateTime;

import java.util.List;

public interface MoneyTransferListProducer {

    public MoneyTransfer getMoneyTransfer(Long id);

    public MoneyTransfer getMoneyTransfer(MoneyTransfer moneyTransfer);

    public MoneyTransfer getMoneyTransfer(Transaction transaction);

    public List<MoneyTransfer> getMoneyTransfers(ExpenseReport expenseReport);

    public List<MoneyTransfer> getMoneyTransfersBetweenDates(DateTime fromDate, DateTime toDate);

    public List<MoneyTransfer> getAllMoneyTransfers();
}
