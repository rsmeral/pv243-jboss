package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.Person;
import javax.ejb.Local;

@Local
public interface PersonRepository {

    void create(Person person);

    void update(Person person);

    void remove(Person person);

}
