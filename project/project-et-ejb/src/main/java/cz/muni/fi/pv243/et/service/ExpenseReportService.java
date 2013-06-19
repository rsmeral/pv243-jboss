package cz.muni.fi.pv243.et.service;

import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.PersonRole;
import cz.muni.fi.pv243.et.model.ReportStatus;
import cz.muni.fi.pv243.et.security.annotation.Roles;

import java.util.Collection;

public interface ExpenseReportService {
    void save(ExpenseReport report);
    void remove(ExpenseReport report);
    void claim(ExpenseReport report, Person verifier);

    ExpenseReport get(Long id);

    Collection<ExpenseReport> findAll();
    Collection<ExpenseReport> findForSubmitter(Person submitter);
    Collection<ExpenseReport> findForVerifier(Person verifier);
    Collection<ExpenseReport> findWithNoVerifierAssigned();
    Collection<ExpenseReport> findByStatus(ReportStatus status);
    Collection<ExpenseReport> findForSubmitterWithStatus(Person submitter, ReportStatus status);
    Collection<ExpenseReport> findForVerifierWithStatus(Person verifier, ReportStatus status);
}
