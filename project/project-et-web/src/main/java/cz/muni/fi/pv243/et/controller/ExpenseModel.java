package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.data.ExpenseReportListProducer;
import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.Person;

import javax.ejb.Stateful;
import javax.enterprise.inject.Produces;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

//@Stateful
@SessionScoped
@Named
public class ExpenseModel implements Serializable {


    private ExpenseReport report;


    @Inject
    private ExpenseReportListProducer erlp;

    @Produces
    @Named("expenseReports")
    public Collection<ExpenseReport> getExpenseReports() {
        return erlp.getAll();
    }

    @Produces
    @Named("submitterExpenseReports")
    public Collection<ExpenseReport> getSubmitterExpenseReports(Person submitter) {
        return erlp.getAllForSubmitter(submitter);
    }

    public void setExpenseReport(Long id) {
        System.out.println("Setting report to id=" + id);
        ExpenseReport rep = erlp.get(id);
        System.out.println("report=" + rep);
        this.report = rep;
    }

    @Produces
    @Named("singleReport")
    public ExpenseReport getExpenseReport(){
        System.out.println("Producing single report " + this.report);
        return this.report;
    }

}
