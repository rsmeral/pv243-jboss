package cz.muni.fi.pv243.et.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Currency;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Money transaction. Used in cz.muni.fi.pv243.et.model.MoneyTransfer or
 * cz.muni.fi.pv243.et.model.Payment
 */
@MappedSuperclass
public class Transaction {

    @NotNull
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datum")
    private DateTime date;

    @NotNull
    @Column(name = "val")
    private BigDecimal value;

    @NotNull
    private String currency;// TODO: how to represent currency? OGM doesn't like java.util.Currency

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.date != null ? this.date.hashCode() : 0);
        hash = 73 * hash + (this.value != null ? this.value.hashCode() : 0);
        hash = 73 * hash + (this.currency != null ? this.currency.hashCode() : 0);
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
        final Transaction other = (Transaction) obj;
        if (this.date != other.date && (this.date == null || !this.date.equals(other.date))) {
            return false;
        }
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        if (this.currency != other.currency && (this.currency == null || !this.currency.equals(other.currency))) {
            return false;
        }
        return true;
    }
}
