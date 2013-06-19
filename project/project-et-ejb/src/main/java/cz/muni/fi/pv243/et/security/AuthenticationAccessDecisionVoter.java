package cz.muni.fi.pv243.et.security;

import cz.muni.fi.pv243.et.message.SecurityMessage;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoterContext;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;
import org.picketlink.Identity;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class AuthenticationAccessDecisionVoter implements AccessDecisionVoter {

    @Inject
    private SecurityMessage message;

    @Inject
    private Identity identity;

    @Override
    public Set<SecurityViolation> checkPermission(AccessDecisionVoterContext accessDecisionVoterContext) {
        Set<SecurityViolation> violations = new HashSet<SecurityViolation>(accessDecisionVoterContext.getViolations());

        if (!identity.isLoggedIn()) {
            final String msg = message.notAuthenticated();
            violations.add(new SecurityViolation() {
                @Override
                public String getReason() {
                    return msg;
                }
            });
        }

        return violations;
    }
}
