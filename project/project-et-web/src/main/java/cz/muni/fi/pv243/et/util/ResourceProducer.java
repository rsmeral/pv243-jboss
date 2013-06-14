package cz.muni.fi.pv243.et.util;

import org.picketlink.Identity;
import org.picketlink.idm.model.Attribute;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

public class ResourceProducer {

    @Inject
    private Identity identity;

    @Produces
    @RequestScoped
    public FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    @Produces
    @Named("currentPersonId")
    @RequestScoped
    public LongHolder getCurrentPersonId() {
        Attribute<String> personId = identity.getUser().<String>getAttribute("personId");
        LongHolder lg = new LongHolder();
        lg.setValue(Long.valueOf(personId.getValue()));
        return lg;
    }
}
