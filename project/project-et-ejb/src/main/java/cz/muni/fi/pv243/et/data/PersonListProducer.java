package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.Person;

import javax.ejb.Local;
import java.util.Collection;

@Local
public interface PersonListProducer {

    Person getPerson(Long id);

    Collection<Person> findAll();

    Person findByEmail(String email);

    Collection<Person> findByName(String firstName, String lastName);
}
