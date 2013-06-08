package cz.muni.fi.pv243.et.security;

import cz.muni.fi.pv243.et.data.PersonRepository;
import cz.muni.fi.pv243.et.message.WebMessage;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.PersonRole;
import cz.muni.fi.pv243.et.security.model.NewUser;
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
    private NewUser newUser;

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
        if (!newUser.getPassword().equals(newUser.getPasswordConfirmation())) {
            this.facesContext.addMessage(null, new FacesMessage(message.passwordMismatch()));
            return null;
        }

        Person person = new Person();
        person.setBankAccount(this.newUser.getBankAccount());
        person.setEmail(this.newUser.getEmail());
        person.setFirstName(this.newUser.getFirstName());
        person.setLastName(this.newUser.getLastName());

        this.personRepository.create(person);
        log.logPersonCreated(person.getId());

        User user = new SimpleUser(this.newUser.getUserName());

        user.setFirstName(this.newUser.getFirstName());
        user.setLastName(this.newUser.getLastName());
        user.setEmail(this.newUser.getEmail());
        user.setAttribute(new Attribute<String>("personId", person.getId().toString()));

        this.identityManager.add(user);

        Password password = new Password(this.newUser.getPassword().toCharArray());

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
        return this.identityManager.getUser(this.newUser.getUserName()) != null;
    }

    private boolean isEmailUsed() {
        return this.identityManager
                .createIdentityQuery(User.class)
                .setParameter(User.EMAIL, this.newUser.getEmail())
                .getResultCount() > 0;
    }

}
