package cz.muni.fi.pv243.et.security;

import cz.muni.fi.pv243.et.model.PersonRole;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.credential.internal.Password;
import org.picketlink.idm.model.Role;
import org.picketlink.idm.model.SimpleRole;
import org.picketlink.idm.model.SimpleUser;
import org.picketlink.idm.model.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class DefaultUserInitializer {

    @Inject
    private SecurityLog log;

    @Inject
    private IdentityManager identityManager;

    @PostConstruct
    private void populateIdentities() {
        log.logInitializingUsers();
        if (identityManager
                .createIdentityQuery(User.class)
                .setParameter(User.LOGIN_NAME, "admin")
                .getResultCount() == 0) {
            User admin = new SimpleUser();
            admin.setLoginName("admin");
            admin.setFirstName("admin");
            admin.setLastName("admin");

            identityManager.add(admin);

            Password password = new Password("admin".toCharArray());
            this.identityManager.updateCredential(admin, password);

            Role adminRole = identityManager.getRole(PersonRole.ADMIN.toString());

            if (adminRole == null) {
                adminRole = new SimpleRole(PersonRole.ADMIN.toString());
                identityManager.add(adminRole);
            }

            identityManager.grantRole(admin, adminRole);
        }
    }


}
