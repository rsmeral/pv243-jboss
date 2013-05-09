package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.ExpenseReport;

import javax.ejb.Local;

@Local
public interface ExpenseReportRepository {

    public void createExpenseReport(ExpenseReport report);

    public void updateExpenseReport(ExpenseReport report);

    public void removeExpenseReport(ExpenseReport report);
}
