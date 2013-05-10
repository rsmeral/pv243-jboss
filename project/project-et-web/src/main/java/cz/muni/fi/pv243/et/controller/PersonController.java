/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.data.PersonListProducer;
import cz.muni.fi.pv243.et.data.PersonRepository;
import cz.muni.fi.pv243.et.model.Person;
import java.util.Collection;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 * @author rsmeral
 */
@Model
public class PersonController {

    @Inject
    private PersonRepository pd;

    @Inject
    private PersonListProducer plp;

    public String createPersons() {
        
        Person person = new Person();

        person.setFirstName("Test");
        person.setLastName("Test");
        person.setEmail("test2@test.com");
        person.setBankAccount("123456789");

        pd.create(person);


        Person personNew = new Person();
        personNew.setFirstName("Test");
        personNew.setLastName("Test");
        personNew.setEmail("test@test.com");
        personNew.setBankAccount("123456789");

        pd.create(personNew);
        System.out.println("createPersons");
        return "created";
    }

    public Collection<Person> getPersons() {
        System.out.println(plp.findAll());

        System.out.println("getPersons");
//        return pd.findAll();
        Collection<Person> people = plp.findByEmail("test2@test.com");
        System.out.println("Person=" + people);

        return people;
    }
}
