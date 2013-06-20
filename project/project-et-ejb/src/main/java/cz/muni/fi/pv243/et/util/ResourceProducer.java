package cz.muni.fi.pv243.et.util;

import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.PersonWrapper;
import org.jboss.solder.logging.Logger;
import org.picketlink.Identity;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

public class ResourceProducer {

    @Inject
    private Identity identity;

//    @Produces
//    public Logger produceLogger(InjectionPoint injectionPoint) {
//        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass());
//    }

    @Produces
    @Model
    @CurrentPerson
    public PersonWrapper getCurrentPerson() {
        return new PersonWrapper(identity.getUser().<Person>getAttribute("person").getValue());
    }

}
