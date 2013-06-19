package cz.muni.fi.pv243.et.model;


import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Indexed
public class Payment extends Transaction implements Serializable {

    @DocumentId
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false, cascade= CascadeType.MERGE)
    @NotNull
    private Purpose purpose;

    @ManyToOne(cascade= CascadeType.MERGE)
    private Receipt receipt;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Payment payment = (Payment) o;

        if (id != null ? !id.equals(payment.id) : payment.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", purpose=" + purpose +
                ", receipt=" + receipt +
                '}';
    }
}
