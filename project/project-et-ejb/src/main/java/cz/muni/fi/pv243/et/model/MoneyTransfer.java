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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MoneyTransfer that = (MoneyTransfer) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
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
