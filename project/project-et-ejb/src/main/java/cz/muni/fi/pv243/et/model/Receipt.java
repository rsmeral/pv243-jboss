package cz.muni.fi.pv243.et.model;

import org.hibernate.search.annotations.Indexed;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Indexed
public class Receipt implements Serializable {

    @Id
    @GeneratedValue
    private Long receiptId;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date importDate;

    @OneToOne(optional = false)//, targetEntity = Person.class)
    private Person importedBy;

    private byte[] document;

    private String documentName;

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long id) {
        this.receiptId = id;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public Person getImportedBy() {
        return importedBy;
    }

    public void setImportedBy(Person importedBy) {
        this.importedBy = importedBy;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.receiptId != null ? this.receiptId.hashCode() : 0);
        hash = 59 * hash + (this.importDate != null ? this.importDate.hashCode() : 0);
        hash = 59 * hash + (this.importedBy != null ? this.importedBy.hashCode() : 0);
        hash = 59 * hash + (this.document != null ? this.document.hashCode() : 0);
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
        final Receipt other = (Receipt) obj;
        if (this.receiptId != other.receiptId && (this.receiptId == null || !this.receiptId.equals(other.receiptId))) {
            return false;
        }
        if (this.importDate != other.importDate && (this.importDate == null || !this.importDate.equals(other.importDate))) {
            return false;
        }
        if (this.importedBy != other.importedBy && (this.importedBy == null || !this.importedBy.equals(other.importedBy))) {
            return false;
        }
        if ((this.document == null) ? (other.document != null) : !this.document.equals(other.document)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Receipt{"
                + "receiptId=" + receiptId
                + ", importDate=" + importDate
                + ", importedBy=" + importedBy.getEmail()
                + ", document='" + document + '\''
                + '}';
    }
}
