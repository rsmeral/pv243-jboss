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
    private Person creator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        if (this.creator != other.creator && (this.creator == null || !this.creator.equals(other.creator))) {
            return false;
        }
        return true;
    }

    public String getExpenseReport(ExpenseReport report) {
        return "  reportId =" + report.getId() + " name=" + report.getName();
    }

    @Override
    public String toString() {
        return "\nMoneyTransfer{" +
                "id=" + id +
                ", creator=" + creator +
                getExpenseReport(super.getReport()) +
                "}\t";
    }
}
