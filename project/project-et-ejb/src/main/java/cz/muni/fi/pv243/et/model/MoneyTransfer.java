package cz.muni.fi.pv243.et.model;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Indexed
public class MoneyTransfer extends Transaction implements Serializable {

    @Id
    @GeneratedValue
    @DocumentId
    @Field
    private Long id;

    @IndexedEmbedded
    @ManyToOne(optional = false)
    @NotNull
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

    private String getExpenseReport() {
        return "  reportId =" + getReport().getId() + " name=" + getReport().getName();
    }

    @Override
    public String toString() {
        return "\nMoneyTransfer{" +
                "id=" + id +
                ", creator=" + creator +
                getExpenseReport() +
                "}\t";
    }
}
