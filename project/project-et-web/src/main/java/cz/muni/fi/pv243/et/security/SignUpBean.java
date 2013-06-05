package cz.muni.fi.pv243.et.security;

import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.credential.internal.Password;
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
    private NewUser newUser;

    private FacesContext facesContext = FacesContext.getCurrentInstance();

    public String signUp() {
        if (isUserNameInUser()) {
            this.facesContext.addMessage(null, new FacesMessage("User ID already in use. Please, choose another one."));
            return null;
        }
        if (!newUser.getPassword().equals(newUser.getPasswordConfirmation())) {
            this.facesContext.addMessage(null, new FacesMessage("Password mismatch."));
            return null;
        }

        User user = new SimpleUser(this.newUser.getUserName());

        user.setFirstName(this.newUser.getFirstName());
        user.setLastName(this.newUser.getLastName());
        user.setEmail(this.newUser.getEmail());

        this.identityManager.add(user);

        Password password = new Password(this.newUser.getPassword().toCharArray());

        this.identityManager.updateCredential(user, password);

        Role guest = this.identityManager.getRole("guest");

        if (guest == null) {
            guest = new SimpleRole("guest");
            this.identityManager.add(guest);
        }

        this.identityManager.grantRole(user, guest);

        Group group = this.identityManager.getGroup("PicketBox Users");

        if (group == null) {
            group = new SimpleGroup("PicketBox Users");
            this.identityManager.add(group);
        }

        this.identityManager.addToGroup(user, group);

        return "/login.xhtml";
    }

    private boolean isUserNameInUser() {
        return this.identityManager.getUser(this.newUser.getUserName()) != null;
    }

}
