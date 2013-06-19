package cz.muni.fi.pv243.et.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.validator.constraints.Email;

@Entity
@Indexed
public class Person implements Serializable {

    @Id
    @GeneratedValue
    @DocumentId
    @Field
    private Long id;

    @Size(min = 2, max = 50)
    @Pattern(regexp = "[A-Z][a-z]*")
    @Field
    private String firstName;

    @Size(min = 2, max = 50)
    @Pattern(regexp = "[A-Z][a-z]*")
    @Field
    private String lastName;

    @Email
    @NotNull
    @Column(unique = true)
    @Field
    private String email;

//    @ElementCollection(targetClass = PersonRole.class)
//    @Enumerated(value = EnumType.ORDINAL)
//    @CollectionTable(name = "PersonRole")
//    @Column(name = "roleId")
//    @LazyCollection(LazyCollectionOption.FALSE)
//    private Set<PersonRole> roles;

    @Digits(integer = 9, fraction = 0)
    @Field
    private String bankAccount;

    public Person() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public Set<PersonRole> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<PersonRole> roles) {
//        this.roles = roles;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != null ? !id.equals(person.id) : person.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                '}';
    }
}