package cz.muni.fi.pv243.et.security;

import cz.muni.fi.pv243.et.data.PersonRepository;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.PersonRole;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.credential.internal.Password;
import org.picketlink.idm.model.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.Serializable;

@Startup
@Singleton
public class DefaultUserInitializer {

    @Inject
    private SecurityLog log;

    @Inject
    private IdentityManager identityManager;

    @Inject
    private PersonRepository personRepository;

    @PostConstruct
    private void populateIdentities() {
        log.logInitializingUsers();
        if (identityManager
                .createIdentityQuery(User.class)
                .setParameter(User.LOGIN_NAME, "admin")
                .getResultCount() == 0) {
            Long id = createAdminPerson();

            User admin = new SimpleUser();
            admin.setLoginName("admin");
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setAttribute(new Attribute<String>("personId", id.toString()));

            identityManager.add(admin);

            Password password = new Password("admin".toCharArray());
            this.identityManager.updateCredential(admin, password);

            Role adminRole = identityManager.getRole(PersonRole.ADMIN.toString());

            if (adminRole == null) {
                adminRole = new SimpleRole(PersonRole.ADMIN.toString());
                identityManager.add(adminRole);
            }

            Role verifierRole = identityManager.getRole(PersonRole.VERIFIER.toString());

            if (verifierRole == null) {
                verifierRole = new SimpleRole(PersonRole.VERIFIER.toString());
                identityManager.add(verifierRole);
            }

            identityManager.grantRole(admin, adminRole);
            identityManager.grantRole(admin, verifierRole);
        }
    }


    private Long createAdminPerson() {
        Person p = new Person();
        p.setFirstName("Admin");
        p.setLastName("Admin");
        p.setBankAccount("00000");
        p.setEmail("admin@admin.sk");

        personRepository.create(p);

        return p.getId();
    }

}
