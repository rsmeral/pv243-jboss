package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.ExpenseReport;

import javax.ejb.Local;

@Local
public interface ExpenseReportRepository {

    void create(ExpenseReport report);

    void update(ExpenseReport report);

    void remove(ExpenseReport report);
}
