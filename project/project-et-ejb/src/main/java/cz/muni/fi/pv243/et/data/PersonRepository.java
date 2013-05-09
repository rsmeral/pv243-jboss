package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.Person;

import javax.ejb.Local;
import java.util.Collection;

@Local
public interface PersonRepository {

    public void create(Person person);

    public void update(Person person);

    public void remove(Person person);

}
