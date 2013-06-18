package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.MoneyTransfer;
import javax.ejb.Local;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Local
public interface MoneyTransferListProducer {

    MoneyTransfer get(Long id);

    Collection<MoneyTransfer> get(ExpenseReport expenseReport);

    List<MoneyTransfer> getAllBetweenDates(Date fromDate, Date toDate);

    Collection<MoneyTransfer> getAll();
}
