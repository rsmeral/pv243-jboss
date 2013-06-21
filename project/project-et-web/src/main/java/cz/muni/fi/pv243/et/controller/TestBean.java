package cz.muni.fi.pv243.et.controller;

import org.jboss.ejb3.annotation.Clustered;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

//@Clustered
@SessionScoped
@Named
public class TestBean implements Serializable {


    private Integer value = 0;


    public Integer test() {
        return value++;
    }


    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
