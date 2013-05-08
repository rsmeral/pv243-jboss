package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.dao.PersonDAO;
import cz.muni.fi.pv243.et.model.Person;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class TestPu {

    @Inject
    private PersonDAO dao;

    @PostConstruct
    public void addPerson() {
        Person person = new Person();

        person.setFirstName("Test");
        person.setLastName("Test");
        person.setEmail("test2@test.com");
        person.setBankAccount("123456789");

        dao.create(person);


        Person personNew = new Person();
        personNew.setFirstName("Test");
        personNew.setLastName("Test");
        personNew.setEmail("test@test.com");
        personNew.setBankAccount("123456789");

        dao.create(personNew);

        System.out.println(dao.load(person.getId()));
        System.out.println(dao.load(personNew.getId()));

        System.out.println(dao.findAll());
        System.out.println(dao.findByEmail("test@test.com"));
    }
}
