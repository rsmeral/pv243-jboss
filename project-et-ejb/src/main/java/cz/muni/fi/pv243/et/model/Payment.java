package cz.muni.fi.pv243.et.model;

import java.util.Currency;

public class Payment {

    private Long id;
    //private BigDecimal value;
    //private Date date;
    private Transaction transaction;
    private Currency currency;
    private Purpose purpose;
    private Receipt receipt;
    private ExpenseReport report;



    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public BigDecimal getValue() {
//        return value;
//    }
//
//    public void setValue(BigDecimal value) {
//        this.value = value;
//    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public ExpenseReport getReport() {
        return report;
    }

    public void setReport(ExpenseReport report) {
        this.report = report;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (!currency.equals(payment.currency)) return false;
        if (!id.equals(payment.id)) return false;
        if (purpose != null ? !purpose.equals(payment.purpose) : payment.purpose != null) return false;
        if (receipt != null ? !receipt.equals(payment.receipt) : payment.receipt != null) return false;
        if (report != null ? !report.equals(payment.report) : payment.report != null) return false;
        if (transaction != null ? !transaction.equals(payment.transaction) : payment.transaction != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (transaction != null ? transaction.hashCode() : 0);
        result = 31 * result + currency.hashCode();
        result = 31 * result + (purpose != null ? purpose.hashCode() : 0);
        result = 31 * result + (receipt != null ? receipt.hashCode() : 0);
        result = 31 * result + (report != null ? report.hashCode() : 0);
        return result;
    }
}

