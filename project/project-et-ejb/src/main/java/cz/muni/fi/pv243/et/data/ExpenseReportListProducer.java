package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.*;

import javax.ejb.Local;
import java.util.Collection;

@Local
public interface ExpenseReportListProducer {

    ExpenseReport get(Long id);

    Collection<ExpenseReport> getAllForSubmitter(Person submitter);

    Collection<ExpenseReport> getAllForVerifier(Person verifier);

    Collection<ExpenseReport> getAllWithNoVerifierAssigned();

    Collection<ExpenseReport> getAllBy(ReportStatus status);
    
    Collection<ExpenseReport> getAllForSubmitterWithStatus(Person submitter, ReportStatus status);

    Collection<ExpenseReport> getAllForVerifierWithStatus(Person verifier, ReportStatus status);

    Collection<ExpenseReport> getAll();
}
