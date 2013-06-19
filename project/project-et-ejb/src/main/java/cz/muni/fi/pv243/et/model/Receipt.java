package cz.muni.fi.pv243.et.model;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Indexed
public class Receipt implements Serializable {

    @Id
    @GeneratedValue
    @DocumentId
    private Long id;

    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull
    private Date importDate;

    @ManyToOne(optional = false)//, targetEntity = Person.class) (cascade= CascadeType.MERGE)
    @NotNull
    @IndexedEmbedded
    private Person importedBy;

    @Lob
    private byte[] document;

    @NotNull
    private String documentName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Receipt receipt = (Receipt) o;

        if (id != null ? !id.equals(receipt.id) : receipt.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", importDate=" + importDate +
                ", importedBy=" + importedBy +
                ", documentName='" + documentName + '\'' +
                '}';
    }
}
