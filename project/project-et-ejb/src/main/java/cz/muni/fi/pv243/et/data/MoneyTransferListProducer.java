package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.MoneyTransfer;
import cz.muni.fi.pv243.et.model.Transaction;
import org.joda.time.DateTime;
import javax.ejb.Local;
import java.util.Collection;
import java.util.List;

@Local
public interface MoneyTransferListProducer {

    public MoneyTransfer getMoneyTransfer(Long id);

    public MoneyTransfer getMoneyTransfer(MoneyTransfer moneyTransfer);

    public MoneyTransfer getMoneyTransfer(Transaction transaction);

    public Collection<MoneyTransfer> getMoneyTransfers(ExpenseReport expenseReport);

    public List<MoneyTransfer> getMoneyTransfersBetweenDates(DateTime fromDate, DateTime toDate);

    public Collection<MoneyTransfer> getAllMoneyTransfers();
}
