package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.message.WebMessage;
import cz.muni.fi.pv243.et.model.PersonRole;
import cz.muni.fi.pv243.et.model.UserModel;
import cz.muni.fi.pv243.et.security.EventLog;
import cz.muni.fi.pv243.et.security.UserManager;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Model
public class PersonController {

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

    @NotNull
    @Size(min = 2, max = 50)
    private String password;

    @NotNull
    @Size(min = 2, max = 50)
    private String confirmPassword;

    private boolean roleAdmin;
    private boolean roleApplicant;
    private boolean roleVerifier;

    @Produces
    @Named
    public Collection<UserModel> getAllPersons() {
        return userManager.findAll();
    }

    public String editPerson(String username) {
        personModel.setUserModel(userManager.get(username));

        return "/secured/editPerson";
    }

    public String createPerson() {
        personModel.setUserModel(new UserModel());

        return "/secured/createPerson";
    }

    public String removePerson(String username) {
        personModel.setUserModel(null);

        userManager.remove(username);

        return "/secured/persons?faces-redirect=true";
    }

    public String changePassword(String username) {
        personModel.setUserModel(userManager.get(username));

        return "/secured/changePassword";
    }

    public String saveNewPassword() {
        if (!password.equals(confirmPassword)) {
            this.facesContext.addMessage(null, new FacesMessage(message.passwordMismatch()));
            return null;
        }

        userManager.changePassword(personModel.getUserModel().getUserName(), password);

        return "/secured/persons?faces-redirect=true";
    }

    public String changeRoles(String username) {
        personModel.setUserModel(userManager.get(username));

        this.roleAdmin = userManager.hasRole(username, PersonRole.ADMIN);
        this.roleApplicant = userManager.hasRole(username, PersonRole.APPLICANT);
        this.roleVerifier = userManager.hasRole(username, PersonRole.VERIFIER);

        return "/secured/changeRoles";
    }

    public String saveRoles() {
        String username = personModel.getUserModel().getUserName();
        if (isRoleAdmin()) {
            userManager.grantRole(username, PersonRole.ADMIN);
        } else {
            userManager.revokeRole(username, PersonRole.ADMIN);
        }
        if (isRoleApplicant()) {
            userManager.grantRole(username, PersonRole.APPLICANT);
        } else {
            userManager.revokeRole(username, PersonRole.APPLICANT);
        }
        if (isRoleVerifier()) {
            userManager.grantRole(username, PersonRole.VERIFIER);
        } else {
            userManager.revokeRole(username, PersonRole.VERIFIER);
        }

        return "/secured/persons?faces-redirect=true";
    }

    public String savePerson() {
        UserModel existing = userManager.get(this.personModel.getUserModel().getUserName());
        Long userId = this.personModel.getUserModel().getId();
        if (existing != null && (userId == null || !userId.equals(existing.getId()))) {
            this.facesContext.addMessage(null, new FacesMessage(message.userIdInUse()));
            return null;
        }
        Collection<UserModel> mailUsers = userManager.findByEmail(this.personModel.getUserModel().getEmail());
        if (isOtherPersonsEmail(this.personModel.getUserModel(), mailUsers)) {
            this.facesContext.addMessage(null, new FacesMessage(message.userEmailInUse()));
            return null;
        }

        boolean newPerson = this.personModel.getUserModel().getId() == null;
        if (newPerson) {
            userManager.add(this.personModel.getUserModel());
        } else {
            userManager.update(this.personModel.getUserModel());
        }

        return "/secured/persons?faces-redirect=true";
    }

    private boolean isOtherPersonsEmail(UserModel model, Collection<UserModel> mailed) {
        if (model.getId() == null && !mailed.isEmpty()) {
            return true;
        }
        for (UserModel um : mailed) {
            if (!um.getId().equals(model.getId())) {
                return true;
            }
        }
        return false;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isRoleAdmin() {
        return roleAdmin;
    }

    public void setRoleAdmin(boolean roleAdmin) {
        this.roleAdmin = roleAdmin;
    }

    public boolean isRoleApplicant() {
        return roleApplicant;
    }

    public void setRoleApplicant(boolean roleApplicant) {
        this.roleApplicant = roleApplicant;
    }

    public boolean isRoleVerifier() {
        return roleVerifier;
    }

    public void setRoleVerifier(boolean roleVerifier) {
        this.roleVerifier = roleVerifier;
    }
}
