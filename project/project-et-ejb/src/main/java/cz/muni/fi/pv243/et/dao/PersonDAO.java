package cz.muni.fi.pv243.et.dao;

import cz.muni.fi.pv243.et.model.Person;

import javax.ejb.Local;
import java.util.Collection;

@Local
public interface PersonDAO {
    Person load(String id);
    void create(Person person);
    void update(Person person);
    void remove(Person person);

    Collection<Person> findAll();
    Collection<Person> findByEmail(String email);
}
