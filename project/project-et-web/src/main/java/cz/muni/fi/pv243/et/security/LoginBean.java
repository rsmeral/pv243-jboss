package cz.muni.fi.pv243.et.security;

import org.picketbox.core.authentication.credential.UsernamePasswordCredential;
import org.picketlink.Identity;
import org.picketlink.extensions.core.pbox.LoginCredential;

import javax.enterprise.context.RequestScoped;
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

    public String login(String userName, String password) {
        this.credential.setCredential(new UsernamePasswordCredential(userName, password));

        this.identity.login();

        if (this.identity.isLoggedIn()) {
            return "/secured/index.xhtml";
        }

        return null;
    }
}
