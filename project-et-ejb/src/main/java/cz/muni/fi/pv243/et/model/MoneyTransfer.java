package cz.muni.fi.pv243.et.model;

public class MoneyTransfer {

    private Long id;
    private Transaction transaction;
    private ExpenseReport report;
    private Person creator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public ExpenseReport getReport() {
        return report;
    }

    public void setReport(ExpenseReport report) {
        this.report = report;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoneyTransfer that = (MoneyTransfer) o;

        if (!creator.equals(that.creator)) return false;
        if (!id.equals(that.id)) return false;
        if (!report.equals(that.report)) return false;
        if (transaction != null ? !transaction.equals(that.transaction) : that.transaction != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (transaction != null ? transaction.hashCode() : 0);
        result = 31 * result + report.hashCode();
        result = 31 * result + creator.hashCode();
        return result;
    }
}
