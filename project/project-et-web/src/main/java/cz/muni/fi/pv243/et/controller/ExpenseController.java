package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.data.PersonListProducer;

import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
@Stateless
public class ExpenseController implements Serializable {
    private static long serialVersionUID = 1234243242343424L; //not generated value!!

    @Inject
    private PersonListProducer plp;


}
