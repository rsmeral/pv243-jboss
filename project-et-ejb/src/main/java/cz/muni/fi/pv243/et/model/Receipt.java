package cz.muni.fi.pv243.et.model;

import java.util.Date;

public class Receipt {

    private Long id;
    private Date importDate;
    private Person importedBy;
    private String file;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Receipt receipt = (Receipt) o;

        if (file != null ? !file.equals(receipt.file) : receipt.file != null) return false;
        if (!id.equals(receipt.id)) return false;
        if (!importDate.equals(receipt.importDate)) return false;
        if (!importedBy.equals(receipt.importedBy)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + importDate.hashCode();
        result = 31 * result + importedBy.hashCode();
        result = 31 * result + (file != null ? file.hashCode() : 0);
        return result;
    }
}
