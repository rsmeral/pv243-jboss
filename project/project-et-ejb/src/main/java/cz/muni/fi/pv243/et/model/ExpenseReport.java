package cz.muni.fi.pv243.et.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Indexed
public class ExpenseReport implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    @ManyToOne
    @IndexedEmbedded
    private Person submitter;

    @ManyToOne(cascade= CascadeType.MERGE)
    @IndexedEmbedded
    private Person verifier;

    @OneToMany(mappedBy = "report", cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Payment> payments;

    @OneToMany(mappedBy = "report", cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MoneyTransfer> moneyTransfers;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSubmittedDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastChangeDate;

    @Field
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(Date lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpenseReport that = (ExpenseReport) o;

        if (approvedDate != null ? !approvedDate.equals(that.approvedDate) : that.approvedDate != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (lastChangeDate != null ? !lastChangeDate.equals(that.lastChangeDate) : that.lastChangeDate != null)
            return false;
        if (lastSubmittedDate != null ? !lastSubmittedDate.equals(that.lastSubmittedDate) : that.lastSubmittedDate != null)
            return false;
        if (moneyTransfers != null ? !moneyTransfers.equals(that.moneyTransfers) : that.moneyTransfers != null)
            return false;
        if (!name.equals(that.name)) return false;
        if (payments != null ? !payments.equals(that.payments) : that.payments != null) return false;
        if (status != that.status) return false;
        if (!submitter.equals(that.submitter)) return false;
        if (verifier != null ? !verifier.equals(that.verifier) : that.verifier != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + submitter.hashCode();
        result = 31 * result + (verifier != null ? verifier.hashCode() : 0);
        result = 31 * result + (payments != null ? payments.hashCode() : 0);
        result = 31 * result + (moneyTransfers != null ? moneyTransfers.hashCode() : 0);
        result = 31 * result + (lastSubmittedDate != null ? lastSubmittedDate.hashCode() : 0);
        result = 31 * result + (approvedDate != null ? approvedDate.hashCode() : 0);
        result = 31 * result + (lastChangeDate != null ? lastChangeDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExpenseReport{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", submitter=" + submitter +
                ", verifier=" + verifier +
                ", payments=" + payments +
                ", moneyTransfers=" + moneyTransfers +
                ", lastSubmittedDate=" + lastSubmittedDate +
                ", approvedDate=" + approvedDate +
                ", lastChangeDate=" + lastChangeDate +
                ", status=" + status +
                '}';
    }
}
