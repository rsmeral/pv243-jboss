package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.message.WebMessage;
import cz.muni.fi.pv243.et.model.UserModel;
import cz.muni.fi.pv243.et.security.SecurityLog;
import cz.muni.fi.pv243.et.security.UserManager;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Model
public class PersonController {

    @Inject
    private PersonModel personModel;

    @Inject
    private FacesContext facesContext;

    @Inject
    private SecurityLog log;

    @Inject
    private WebMessage message;

    @Inject
    private UserManager userManager;

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
        if (!personModel.getUserModel().getPassword().equals(personModel.getUserModel().getPasswordConfirmation())) {
            this.facesContext.addMessage(null, new FacesMessage(message.passwordMismatch()));
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

}
