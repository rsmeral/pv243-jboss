package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.model.Purpose;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
public class PurposeModel implements Serializable {

    private Purpose purpose;

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    @Produces
    @Named("purpose")
    public Purpose getPurpose() {
        return purpose;
    }
}
