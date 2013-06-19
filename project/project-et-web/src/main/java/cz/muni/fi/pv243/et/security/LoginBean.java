package cz.muni.fi.pv243.et.security;

import cz.muni.fi.pv243.et.data.PersonListProducer;
import cz.muni.fi.pv243.et.message.WebMessage;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.service.ReceiptService;
import org.picketbox.core.authentication.credential.UsernamePasswordCredential;
import org.picketlink.Identity;
import org.picketlink.authentication.AuthenticationException;
import org.picketlink.extensions.core.pbox.LoginCredential;
import org.picketlink.idm.model.Attribute;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@RequestScoped
@Named
public class LoginBean {
    private static final long serialVersionUID = 1L;

    @Inject
    private Identity identity;

    @Inject
    private LoginCredential credential;

    @Inject
    private FacesContext facesContext;

    @Inject
    private PersonListProducer personListProducer;

    @Inject
    private ReceiptService receiptService;

    @Inject
    private WebMessage webMessage;

    public String login(String userName, String password) throws AuthenticationException {
        if (userName.equals("tomas")) {
            receiptService.findFromDate(new java.util.Date());
        }
        this.credential.setCredential(new UsernamePasswordCredential(userName, password));

        this.identity.login();

        if (this.identity.isLoggedIn()) {
            Attribute<String> personId = identity.getUser().<String>getAttribute("personId");
            Person person = personListProducer.getPerson(Long.valueOf(personId.getValue()));
            identity.getUser().setAttribute(new Attribute<Person>("person", person));

            return "/secured/index.xhtml";
        }

        this.facesContext.addMessage(null, new FacesMessage(webMessage.loginFailed()));

        return null;
    }
}
