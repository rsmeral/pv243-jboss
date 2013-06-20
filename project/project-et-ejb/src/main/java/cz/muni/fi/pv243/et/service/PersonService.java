package cz.muni.fi.pv243.et.service;

import cz.muni.fi.pv243.et.model.Person;

import java.util.Collection;

public interface PersonService {
    void save(Person person);
    void remove(Person person);
    Person get(Long id);
    Collection<Person> findAll();
    Person findByEmail(String email);
    Collection<Person> findByName(String firstName, String lastName);
}
