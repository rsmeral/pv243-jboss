package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.Person;
import javax.ejb.Local;
import java.util.Collection;

@Local
public interface PersonListProducer {

    public Person getPerson(Long id);

    public Collection<Person> findAll();

    public Person findByEmail(String email);

    public Collection<Person> findByName(String firstName, String lastName);


}
