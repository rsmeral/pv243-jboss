/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.dao.PersonDAO;
import cz.muni.fi.pv243.et.model.Person;
import java.util.Collection;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 * @author rsmeral
 */
@Model
public class PersonController {

    @Inject
    private PersonDAO pd;

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
        System.out.println("getPersons");
//        return pd.findAll();
        return pd.findByEmail("test2@test.com");
    }
}
