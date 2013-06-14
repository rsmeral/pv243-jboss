package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.data.*;
import cz.muni.fi.pv243.et.model.*;
import cz.muni.fi.pv243.et.model.Currency;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;


/**
 * Service layer (?)
 */

@SessionScoped
public class ExpenseModel implements Serializable {

    private ExpenseReport report;
    private Payment payment;
    private MoneyTransfer moneyTransfer;

    // is it too ugly to set both as "actual" results from which we'll compute other stuff?
    // we'll always set those 2 variables in JPQL call, so we will not ask DB twice
    // ??
    private List<MoneyTransfer> moneyTransfers;
    private List<Payment> payments;

    @Inject
    private ExpenseReportListProducer expenseReportListProducer;

    @Inject
    private MoneyTransferListProducer moneyTransferListProducer;

    @Inject
    private PaymentListProducer paymentListProducer;

    @Inject
    private ReceiptListProducer receiptListProducer;

    @Inject
    private PurposeListProducer purposeListProducer;

    @Produces
    @Named("expenseReports")
    public Collection<ExpenseReport> getExpenseReports() {
        return expenseReportListProducer.getAll();
    }

    @Produces
    @Named("submitterExpenseReports")
    public Collection<ExpenseReport> getSubmitterExpenseReports(Person submitter) {
        return expenseReportListProducer.getAllForSubmitter(submitter);
    }

    public void setExpenseReport(Long id) {
        ExpenseReport rep = expenseReportListProducer.get(id);
        this.report = rep;
    }

    @Produces
    @RequestScoped
    @Named("singleReport")
    public ExpenseReport getExpenseReport() {
        return report;
    }

    @Produces
    @RequestScoped
    @Named("moneyTransfers")
    public List<MoneyTransfer> getMoneyTransfers() {
        if (this.report == null) {
            throw new NullPointerException("report is null");
        }
        moneyTransfers = (List) moneyTransferListProducer.get(report);
        return moneyTransfers;
    }

    @Produces
    @RequestScoped
    @Named("payments")
    public List<Payment> getPayments() {
        if (this.report == null) {
            throw new NullPointerException("report is null");
        }

        payments = (List) paymentListProducer.get(report);
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

    @Produces
    @RequestScoped
    @Named("singlePayment")
    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Long id) {
        for (Payment p : getCurrentPayments()) {
            if (p.getId() == id) {
                this.payment = p;
            }
        }
    }

    @Produces
    @RequestScoped
    @Named("singleMoneyTransfer")
    public MoneyTransfer getMoneyTransfer() {
        return moneyTransfer;
    }

    public void setMoneyTransfer(Long id) {
        for (MoneyTransfer mt : getCurrentMoneyTransfers()) {
            if (mt.getId() == id) {
                this.moneyTransfer = mt;
            }
        }
    }


    @Produces
    @Named("receipts")
    public Collection<Receipt> getReceipts() {
        return receiptListProducer.getAllReceipts();
    }

    @Produces
    @Named("currencies")
    public Currency[] getCurrencies() {
        return Currency.values();
    }
}
