package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.*;

import javax.ejb.Local;
import java.util.Collection;

@Local
public interface ExpenseReportListProducer {

    public ExpenseReport get(Long id);

    public Collection<ExpenseReport> getAllForSubmitter(Person submitter);

    public Collection<ExpenseReport> getAllBy(ReportStatus status);

    public Collection<ExpenseReport> getAll();
}
