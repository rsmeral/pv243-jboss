package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.data.ExpenseReportListProducer;
import cz.muni.fi.pv243.et.data.MoneyTransferListProducer;
import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.MoneyTransfer;
import cz.muni.fi.pv243.et.model.Person;
import org.hibernate.Hibernate;

import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 * Service layer (?)
 */

@SessionScoped
public class ExpenseModel implements Serializable {


    private ExpenseReport report;
    private List<MoneyTransfer> mts;


    @Inject
    private ExpenseReportListProducer erlp;

    @Inject
    private MoneyTransferListProducer mtlp;


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
        this.report = rep;
    }

    @Produces
    @RequestScoped
    @Named("singleReport")
    public ExpenseReport getExpenseReport(){
        System.out.println("Producing single report " + report);
        return report;
    }

    @Produces
    @RequestScoped
    @Named("moneyTransfers")
    public List<MoneyTransfer> getMoneyTransfers() {
        if (this.report == null) {
            throw new NullPointerException("report is null");
        }
        return (List) mtlp.get(report);

    }

}
