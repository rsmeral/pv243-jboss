package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.data.ExpenseReportListProducer;
import cz.muni.fi.pv243.et.data.MoneyTransferListProducer;
import cz.muni.fi.pv243.et.data.PaymentListProducer;
import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.MoneyTransfer;
import cz.muni.fi.pv243.et.model.Payment;
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
//@Stateful (?)
public class ExpenseModel implements Serializable {


    private ExpenseReport report;

    // is it too ugly to set both as "actual" results from which we'll compute other stuff?
    // we'll always set those 2 variables in JPQL call, so we will not ask DB twice
    // ??
    private List<MoneyTransfer> moneyTransfers;
    private List<Payment> payments;

    @Inject
    private ExpenseReportListProducer erlp;

    @Inject
    private MoneyTransferListProducer mtlp;

    @Inject
    private PaymentListProducer plp;

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
        moneyTransfers = (List) mtlp.get(report);
        return moneyTransfers;
    }

    @Produces
    @RequestScoped
    @Named("payments")
    public List<Payment> getPayments() {
        if (this.report == null) {
            throw new NullPointerException("report is null");
        }

        payments = (List) plp.get(report);
        return payments;
    }

    public List<Payment> getCurrentPayments() {
        if (payments == null) {
            payments = getPayments();
        }
        System.out.println("getCurrentPayments=" + payments);
        return payments;
    }

    public List<MoneyTransfer> getCurrentMoneyTransfers() {
        if (moneyTransfers == null) {
            moneyTransfers = getMoneyTransfers();
        }
        System.out.println("getCurrentMoneyTransfer=" + moneyTransfers);
        return moneyTransfers;
    }
}
