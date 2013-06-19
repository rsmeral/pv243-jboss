package cz.muni.fi.pv243.et.model;

import java.io.Serializable;

public class PersonWrapper implements Serializable {

    private Person person;

    public PersonWrapper() {
    }

    public PersonWrapper(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
