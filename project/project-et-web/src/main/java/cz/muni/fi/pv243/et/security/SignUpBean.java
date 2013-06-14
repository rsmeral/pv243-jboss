package cz.muni.fi.pv243.et.security;

import cz.muni.fi.pv243.et.data.PersonRepository;
import cz.muni.fi.pv243.et.message.WebMessage;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.PersonRole;
import cz.muni.fi.pv243.et.model.UserModel;
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
    private PersonRepository personRepository;

    @Inject
    @Named("userModel")
    private UserModel userModel;

    @Inject
    private FacesContext facesContext;

    @Inject
    private SecurityLog log;

    @Inject
    private WebMessage message;

    public String signUp() {
        if (isUserNameInUser()) {
            this.facesContext.addMessage(null, new FacesMessage(message.userIdInUse()));
            return null;
        }
        if (isEmailUsed()) {
            this.facesContext.addMessage(null, new FacesMessage(message.userEmailInUse()));
            return null;
        }
        if (!userModel.getPassword().equals(userModel.getPasswordConfirmation())) {
            this.facesContext.addMessage(null, new FacesMessage(message.passwordMismatch()));
            return null;
        }

        Person person = new Person();
        person.setBankAccount(this.userModel.getBankAccount());
        person.setEmail(this.userModel.getEmail());
        person.setFirstName(this.userModel.getFirstName());
        person.setLastName(this.userModel.getLastName());

        this.personRepository.create(person);
        log.logPersonCreated(person.getId());

        User user = new SimpleUser(this.userModel.getUserName());

        user.setFirstName(this.userModel.getFirstName());
        user.setLastName(this.userModel.getLastName());
        user.setEmail(this.userModel.getEmail());
        user.setAttribute(new Attribute<String>("personId", person.getId().toString()));

        this.identityManager.add(user);

        Password password = new Password(this.userModel.getPassword().toCharArray());

        this.identityManager.updateCredential(user, password);

        Role applicant = this.identityManager.getRole(PersonRole.APPLICANT.toString());

        if (applicant == null) {
            applicant = new SimpleRole(PersonRole.APPLICANT.toString());
            this.identityManager.add(applicant);
        }

        this.identityManager.grantRole(user, applicant);

        return "/login.xhtml";
    }

    private boolean isUserNameInUser() {
        return this.identityManager.getUser(this.userModel.getUserName()) != null;
    }

    private boolean isEmailUsed() {
        return this.identityManager
                .createIdentityQuery(User.class)
                .setParameter(User.EMAIL, this.userModel.getEmail())
                .getResultCount() > 0;
    }

}
