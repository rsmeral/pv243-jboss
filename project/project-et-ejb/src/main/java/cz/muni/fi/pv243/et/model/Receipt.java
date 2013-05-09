package cz.muni.fi.pv243.et.model;

import org.hibernate.search.annotations.Field;
import org.joda.time.DateTime;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

@Entity
public class Receipt implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Temporal(javax.persistence.TemporalType.DATE)
    private DateTime importDate;

    @OneToOne(optional = false)
    private Person importedBy;

    @Field
    private String document;// WHAT THE @#*&^*$@ ?


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getImportDate() {
        return importDate;
    }

    public void setImportDate(DateTime importDate) {
        this.importDate = importDate;
    }

    public Person getImportedBy() {
        return importedBy;
    }

    public void setImportedBy(Person importedBy) {
        this.importedBy = importedBy;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
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
}
