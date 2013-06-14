package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.UserModel;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
public class PersonModel implements Serializable {

//    private Person person;

    private UserModel userModel;

    @Produces
    @Named("userPerson")
    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

//    @Produces
//    @Named("person")
//    public Person getPerson() {
//        return person;
//    }
//
//    public void setPerson(Person person) {
//        this.person = person;
//    }
}
