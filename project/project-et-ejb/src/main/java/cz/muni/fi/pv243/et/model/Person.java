package cz.muni.fi.pv243.et.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.validator.constraints.Email;

@Entity
@Indexed
public class Person implements Serializable {

    @Id
    @GeneratedValue
    private Long personId;

    @Size(min = 2, max = 50)
    @Pattern(regexp = "[A-Z][a-z]*")
    private String firstName;

    @Size(min = 2, max = 50)
    @Pattern(regexp = "[A-Z][a-z]*")
    private String lastName;

    @Email
    @NotNull
    @Column(unique = true)
    private String email;

    @ElementCollection(targetClass = Role.class)
    @Enumerated(value = EnumType.ORDINAL)
    @CollectionTable(name = "PersonRole")
    @Column(name = "roleId")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Role> roles;

    @Digits(integer = 9, fraction = 0)
    private String bankAccount;

    public Person() {
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long id) {
        this.personId = id;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (bankAccount != null ? !bankAccount.equals(person.bankAccount) : person.bankAccount != null) return false;
        if (email != null ? !email.equals(person.email) : person.email != null) return false;
        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) return false;
        if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null) return false;
        if (personId != null ? !personId.equals(person.personId) : person.personId != null) return false;
        if (roles != null ? !roles.equals(person.roles) : person.roles != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = personId != null ? personId.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (bankAccount != null ? bankAccount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", bankAccount='" + bankAccount + '\'' +
                '}';
    }
}