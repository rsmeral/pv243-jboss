package cz.muni.fi.pv243.et.model;

import org.apache.lucene.analysis.KeywordAnalyzer;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Indexed
public class Payment extends Transaction {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private Purpose purpose;

    @ManyToOne
    //@Field(analyze = Analyze.YES, analyzer = @Analyzer(impl = KeywordAnalyzer.class))
    private Receipt receipt;

    @ManyToOne(optional = false)
    private ExpenseReport report;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 53 * hash + (this.purpose != null ? this.purpose.hashCode() : 0);
        hash = 53 * hash + (this.receipt != null ? this.receipt.hashCode() : 0);
        hash = 53 * hash + (this.report != null ? this.report.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Payment other = (Payment) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.purpose != other.purpose && (this.purpose == null || !this.purpose.equals(other.purpose))) {
            return false;
        }
        if (this.receipt != other.receipt && (this.receipt == null || !this.receipt.equals(other.receipt))) {
            return false;
        }
        if (this.report != other.report && (this.report == null || !this.report.equals(other.report))) {
            return false;
        }
        return true;
    }
}
