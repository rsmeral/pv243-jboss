package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.service.ExpenseReportService;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

@Model
public class ExpenseController {

    @Inject
    private ExpenseModel model;

    @Inject
    private ExpenseReportService service;

    @Produces
    @Named("expenseReports")
    public Collection<ExpenseReport> getAllReports() {
        return service.findAll();
    }

    public String showSingleReport(Long id) {
        model.setReport(service.get(id));

        return "report";
    }

}
