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

    public MoneyTransfer get(Long id);

    public Collection<MoneyTransfer> get(ExpenseReport expenseReport);

    public List<MoneyTransfer> getAllBetweenDates(DateTime fromDate, DateTime toDate);

    public Collection<MoneyTransfer> getAll();
}
