package cz.muni.fi.pv243.et.model;

import org.hibernate.search.annotations.Indexed;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Indexed
public class MoneyTransfer extends Transaction implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private ExpenseReport report;

    @OneToOne(optional = false)
    private Person creator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 97 * hash + (this.report != null ? this.report.hashCode() : 0);
        hash = 97 * hash + (this.creator != null ? this.creator.hashCode() : 0);
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
        final MoneyTransfer other = (MoneyTransfer) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.report != other.report && (this.report == null || !this.report.equals(other.report))) {
            return false;
        }
        if (this.creator != other.creator && (this.creator == null || !this.creator.equals(other.creator))) {
            return false;
        }
        return true;
    }
}
