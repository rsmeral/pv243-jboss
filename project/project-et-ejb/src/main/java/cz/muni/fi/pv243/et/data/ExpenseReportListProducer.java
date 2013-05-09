package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.*;

import javax.ejb.Local;
import java.util.Collection;

@Local
public interface ExpenseReportListProducer {

    public ExpenseReport getExpenseReport(Long id);

    public Collection<ExpenseReport> getExpenseReports(Person person);

    public Collection<ExpenseReport> getExpenseReports(MoneyTransfer moneyTransfer);

    public Collection<ExpenseReport> getExpenseReports(Payment payment);

    public Collection<ExpenseReport> getExpenseReports(ReportStatus status);

    public Collection<ExpenseReport> getAllExpenseReports();
}
