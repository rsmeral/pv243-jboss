package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.model.Purpose;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
public class PurposeModel implements Serializable {

    private Purpose purpose;

    private String backUrl;

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    @Produces
    @Named("purpose")
    public Purpose getPurpose() {
        return purpose;
    }
}
