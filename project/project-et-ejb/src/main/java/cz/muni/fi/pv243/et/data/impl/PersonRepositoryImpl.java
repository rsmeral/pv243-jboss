package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.PersonRepository;
import cz.muni.fi.pv243.et.model.Person;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class PersonRepositoryImpl implements PersonRepository {

    @Inject
    private EntityManager em;

    @Override
    public void create(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("person is null");
        }
        if (person.getPersonId() != null) {
            throw new IllegalArgumentException("person id is not null");
        }
        em.persist(person);
    }

    @Override
    public void update(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("person is null");
        }
        if (person.getPersonId() == null) {
            throw new IllegalArgumentException("person id is null");
        }
        em.merge(person);
    }

    @Override
    public void remove(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("person is null");
        }
        if (person.getPersonId() == null) {
            throw new IllegalArgumentException("person id is null");
        }
        em.remove(em.find(Person.class, person.getPersonId()));
    }


}
