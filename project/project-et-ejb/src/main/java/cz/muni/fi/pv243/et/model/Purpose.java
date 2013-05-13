package cz.muni.fi.pv243.et.model;

import org.apache.lucene.analysis.KeywordAnalyzer;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.*;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Indexed
@Embeddable
public class Purpose implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @DocumentId
    private Long id;

    @Size(max = 25)
    @NotNull
    //@Field(analyze = Analyze.YES, analyzer = @Analyzer(impl = KeywordAnalyzer.class))
    private String name;

    @Size(max = 100)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Purpose purpose = (Purpose) o;

        if (description != null ? !description.equals(purpose.description) : purpose.description != null) {
            return false;
        }
        if (!name.equals(purpose.name)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Purpose{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
