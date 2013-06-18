package cz.muni.fi.pv243.et.service.impl;

import cz.muni.fi.pv243.et.data.ExpenseReportListProducer;
import cz.muni.fi.pv243.et.data.ExpenseReportRepository;
import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.ReportStatus;
import cz.muni.fi.pv243.et.service.ExpenseReportService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

@Stateless
public class ExpenseReportServiceImpl implements ExpenseReportService {

    @Inject
    private ExpenseReportRepository repository;

    @Inject
    private ExpenseReportListProducer listProducer;

    @Override
    public void save(ExpenseReport report) {
        if (report == null) {
            throw new IllegalArgumentException();
        }

        if (report.getId() == null) {
            repository.create(report);
        } else {
            repository.update(report);
        }
    }

    @Override
    public void remove(ExpenseReport report) {
        if (report == null) {
            throw new IllegalArgumentException();
        }

        repository.remove(report);
    }

    @Override
    public ExpenseReport get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }

        return listProducer.get(id);
    }

    @Override
    public Collection<ExpenseReport> findAll() {
        return listProducer.getAll();
    }

    @Override
    public Collection<ExpenseReport> findForSubmitter(Person submitter) {
        if (submitter == null) {
            throw new IllegalArgumentException();
        }

        return listProducer.getAllForSubmitter(submitter);
    }

    @Override
    public Collection<ExpenseReport> findByStatus(ReportStatus status) {
        if (status == null) {
            throw new IllegalArgumentException();
        }

        return listProducer.getAllBy(status);
    }
}
