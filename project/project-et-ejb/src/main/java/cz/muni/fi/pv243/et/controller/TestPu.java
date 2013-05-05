package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.model.Person;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class TestPu {

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void addPerson() {
        Person person = new Person();

        person.setFirstName("Test");
        person.setLastName("Test");
        person.setEmail("test@test.com");
        person.setBankAccount("123456789");

        em.persist(person);
        em.flush();

        Person person2 = em.find(Person.class, person.getId());

        System.out.println(person2.getId());
    }
}
