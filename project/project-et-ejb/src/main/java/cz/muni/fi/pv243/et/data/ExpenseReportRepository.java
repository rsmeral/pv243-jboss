package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.ExpenseReport;

import javax.ejb.Local;

@Local
public interface ExpenseReportRepository {

    public void create(ExpenseReport report);

    public void update(ExpenseReport report);

    public void remove(ExpenseReport report);
}
