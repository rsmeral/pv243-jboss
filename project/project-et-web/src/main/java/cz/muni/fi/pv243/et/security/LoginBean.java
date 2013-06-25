package cz.muni.fi.pv243.et.security;

import cz.muni.fi.pv243.et.message.WebMessage;
import cz.muni.fi.pv243.et.service.LoginService;
import org.picketlink.Identity;
import org.picketlink.authentication.AuthenticationException;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Model
public class LoginBean {

    private static final long serialVersionUID = 1L;

    @Inject
    private Identity identity;

    @Inject
    private FacesContext facesContext;

    @Inject
    private WebMessage webMessage;

    @Inject
    private LoginService loginService;

    public String login(String userName, String password) throws AuthenticationException {

        loginService.login(userName, password);

        if (!identity.isLoggedIn()) {
            this.facesContext.addMessage(null, new FacesMessage(webMessage.loginFailed()));
            return null;
        }

        return "/secured/index.xhtml?faces-redirect=true";
    }
}
