package cz.muni.fi.pv243.et.service.impl;

import cz.muni.fi.pv243.et.data.PersonListProducer;
import cz.muni.fi.pv243.et.data.PersonRepository;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.PersonRole;
import cz.muni.fi.pv243.et.security.annotation.Authenticated;
import cz.muni.fi.pv243.et.security.annotation.Roles;
import cz.muni.fi.pv243.et.service.PersonService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

@Stateless
public class PersonServiceImpl implements PersonService {

    @Inject
    private PersonRepository repository;

    @Inject
    private PersonListProducer listProducer;

    @Override
    public void save(Person person) {
        if (person == null) {
            throw new IllegalArgumentException();
        }

        if (person.getId() == null) {
            repository.create(person);
        } else {
            repository.update(person);
        }
    }

    @Override
    public void remove(Person person) {
        if (person == null) {
            throw new IllegalArgumentException();
        }

        repository.remove(person);
    }

    @Override
    public Person get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }

        return listProducer.getPerson(id);
    }

    @Authenticated
    @Roles({PersonRole.ADMIN})
    @Override
    public Collection<Person> findAll() {
        return listProducer.findAll();
    }

    @Override
    public Person findByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException();
        }

        return listProducer.findByEmail(email);
    }

    @Override
    public Collection<Person> findByName(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException();
        }

        return listProducer.findByName(firstName, lastName);
    }
}
