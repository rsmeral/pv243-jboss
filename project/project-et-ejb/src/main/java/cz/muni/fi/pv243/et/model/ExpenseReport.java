package cz.muni.fi.pv243.et.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
public class ExpenseReport implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne
    private Person submitter;

    @ManyToOne
    private Person verifier;

    @OneToMany(mappedBy = "report")
    private List<Payment> payments;

    @OneToMany(mappedBy = "report")
    private List<MoneyTransfer> moneyTransfers;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSubmittedDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;

    @Enumerated(EnumType.ORDINAL)
    private ReportStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Person submitter) {
        this.submitter = submitter;
    }

    public Person getVerifier() {
        return verifier;
    }

    public void setVerifier(Person verifier) {
        this.verifier = verifier;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<MoneyTransfer> getMoneyTransfers() {
        return moneyTransfers;
    }

    public void setMoneyTransfers(List<MoneyTransfer> moneyTransfers) {
        this.moneyTransfers = moneyTransfers;
    }

    public Date getLastSubmittedDate() {
        return lastSubmittedDate;
    }

    public void setLastSubmittedDate(Date lastSubmittedDate) {
        this.lastSubmittedDate = lastSubmittedDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExpenseReport that = (ExpenseReport) o;

        if (approvedDate != null ? !approvedDate.equals(that.approvedDate) : that.approvedDate != null) {
            return false;
        }
        if (!id.equals(that.id)) {
            return false;
        }
        if (!lastSubmittedDate.equals(that.lastSubmittedDate)) {
            return false;
        }
        if (moneyTransfers != null ? !moneyTransfers.equals(that.moneyTransfers) : that.moneyTransfers != null) {
            return false;
        }
        if (payments != null ? !payments.equals(that.payments) : that.payments != null) {
            return false;
        }
        if (status != that.status) {
            return false;
        }
        if (!submitter.equals(that.submitter)) {
            return false;
        }
        if (verifier != null ? !verifier.equals(that.verifier) : that.verifier != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + submitter.hashCode();
        result = 31 * result + (verifier != null ? verifier.hashCode() : 0);
        result = 31 * result + (payments != null ? payments.hashCode() : 0);
        result = 31 * result + (moneyTransfers != null ? moneyTransfers.hashCode() : 0);
        result = 31 * result + lastSubmittedDate.hashCode();
        result = 31 * result + (approvedDate != null ? approvedDate.hashCode() : 0);
        result = 31 * result + status.hashCode();
        return result;
    }
}
