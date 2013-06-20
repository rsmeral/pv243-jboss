package cz.muni.fi.pv243.et.service.impl;

import cz.muni.fi.pv243.et.data.PersonListProducer;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.service.LoginService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.picketbox.core.authentication.credential.UsernamePasswordCredential;
import org.picketlink.Identity;
import org.picketlink.authentication.AuthenticationException;
import org.picketlink.extensions.core.pbox.LoginCredential;
import org.picketlink.idm.model.Attribute;

@ApplicationScoped
public class LoginServiceImpl implements LoginService {

    @Inject
    private Identity identity;

    @Inject
    private LoginCredential credential;

    @Inject
    private PersonListProducer personListProducer;

    public void login(String userName, String password) throws AuthenticationException {
        this.credential.setCredential(new UsernamePasswordCredential(userName, password));

        this.identity.login();

        if (this.identity.isLoggedIn()) {
            Attribute<String> personId = identity.getUser().<String>getAttribute("personId");
            Person person = personListProducer.getPerson(Long.valueOf(personId.getValue()));
            identity.getUser().setAttribute(new Attribute<Person>("person", person));
        }
    }
}
