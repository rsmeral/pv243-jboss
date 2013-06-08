package cz.muni.fi.pv243.et.security;

import cz.muni.fi.pv243.et.message.WebMessage;
import org.picketbox.core.authentication.credential.UsernamePasswordCredential;
import org.picketlink.Identity;
import org.picketlink.authentication.AuthenticationException;
import org.picketlink.extensions.core.pbox.LoginCredential;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

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
    private WebMessage webMessage;

    public String login(String userName, String password) throws AuthenticationException {
        this.credential.setCredential(new UsernamePasswordCredential(userName, password));

        this.identity.login();

        if (this.identity.isLoggedIn()) {
            return "/secured/index.xhtml";
        }

        this.facesContext.addMessage(null, new FacesMessage(webMessage.loginFailed()));

        return null;
    }
}
