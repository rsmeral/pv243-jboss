package cz.muni.fi.pv243.et.util;


import cz.muni.fi.pv243.et.model.Currency;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Named;

public class ResourceProducer {

    @Produces
    @RequestScoped
    public FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    @Produces
    @Named("currencies")
    public Currency[] getCurrencies() {
        return Currency.values();
    }

}
