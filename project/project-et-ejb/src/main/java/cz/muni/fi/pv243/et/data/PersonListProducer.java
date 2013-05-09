package cz.muni.fi.pv243.et.data;


import cz.muni.fi.pv243.et.model.Person;

import java.util.Collection;

public interface PersonListProducer {

    public Person getPerson(String id);

    public Collection<Person> findAll();

    public Collection<Person> findByEmail(String email);

    public Collection<Person> findByName(String firstName, String lastName);


}
