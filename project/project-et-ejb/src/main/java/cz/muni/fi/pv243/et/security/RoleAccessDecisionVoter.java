package cz.muni.fi.pv243.et.security;

import cz.muni.fi.pv243.et.message.SecurityMessage;
import cz.muni.fi.pv243.et.model.PersonRole;
import cz.muni.fi.pv243.et.security.annotation.Roles;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoterContext;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;
import org.picketlink.extensions.core.pbox.PicketBoxIdentity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class RoleAccessDecisionVoter implements AccessDecisionVoter {

    @Inject
    private PicketBoxIdentity identity;

    @Inject
    private SecurityMessage secMessage;

    @Override
    public Set<SecurityViolation> checkPermission(AccessDecisionVoterContext accessDecisionVoterContext) {
        Roles roles = accessDecisionVoterContext.getMetaDataFor(Roles.class.getName(), Roles.class);

        Set<SecurityViolation> result = new HashSet<SecurityViolation>(accessDecisionVoterContext.getViolations());
        boolean ok = !roles.anyRole();
        for (PersonRole r : roles.value()) {
            if (!identity.getUserContext().hasRole(r.toString())) {
                if (!roles.anyRole()) {
                    ok = false;
                    break;
                }
            } else {
                if (roles.anyRole()) {
                    ok = true;
                    break;
                }
            }
        }
        System.out.println("validation ok> " + ok);
        if (!ok) {
            final String msg = secMessage.roleMissing();
            result.add(new SecurityViolation() {
                @Override
                public String getReason() {
                    return msg;
                }
            });
        }

        return result;
    }
}
