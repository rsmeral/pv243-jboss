package cz.muni.fi.pv243.et.security;

import cz.muni.fi.pv243.et.controller.PersonModel;
import cz.muni.fi.pv243.et.data.PersonRepository;
import cz.muni.fi.pv243.et.message.WebMessage;
import cz.muni.fi.pv243.et.model.PersonRole;
import cz.muni.fi.pv243.et.model.UserModel;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.model.*;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Stateless
@Named
public class SignUpBean {

    @Inject
    private IdentityManager identityManager;

    @Inject
    private PersonModel personModel;

    @Inject
    private FacesContext facesContext;

    @Inject
    private EventLog log;

    @Inject
    private WebMessage message;

    @Inject
    private UserManager userManager;

    public String signUp() {
        if (isUserNameInUser()) {
            this.facesContext.addMessage(null, new FacesMessage(message.userIdInUse()));
            return null;
        }
        if (isEmailUsed()) {
            this.facesContext.addMessage(null, new FacesMessage(message.userEmailInUse()));
            return null;
        }
        if (!personModel.getUserModel().getPassword().equals(personModel.getUserModel().getPasswordConfirmation())) {
            this.facesContext.addMessage(null, new FacesMessage(message.passwordMismatch()));
            return null;
        }

        userManager.add(this.personModel.getUserModel());
        userManager.changePassword(this.personModel.getUserModel().getUserName(), this.personModel.getUserModel().getPassword());
        userManager.grantRole(this.personModel.getUserModel().getUserName(), PersonRole.APPLICANT);

        return "/login?faces-redirect=true";
    }

    private boolean isUserNameInUser() {
        return this.identityManager.getUser(this.personModel.getUserModel().getUserName()) != null;
    }

    private boolean isEmailUsed() {
        return this.identityManager
                .createIdentityQuery(User.class)
                .setParameter(User.EMAIL, this.personModel.getUserModel().getEmail())
                .getResultCount() > 0;
    }

}
