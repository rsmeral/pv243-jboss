package cz.muni.fi.pv243.et.security;

import cz.muni.fi.pv243.et.message.SecurityMessage;
import cz.muni.fi.pv243.et.model.PersonRole;
import cz.muni.fi.pv243.et.security.annotation.Roles;
import org.apache.deltaspike.core.api.exception.control.event.ExceptionToCatchEvent;
import org.apache.deltaspike.security.api.authorization.AccessDeniedException;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;
import org.jboss.solder.logging.Logger;
import org.picketlink.extensions.core.pbox.PicketBoxIdentity;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.HashSet;
import java.util.Set;

@Roles
@Interceptor
public class RoleBasedAuthorizationInterceptor {

    @Inject
    private PicketBoxIdentity identity;

    @Inject
    private SecurityMessage secMessage;

    @Inject
    private Logger logger;

    @Inject
    private Event<ExceptionToCatchEvent> event;

    @AroundInvoke
    public Object authorizeBasedOnRoles(InvocationContext ctx) throws Exception {
        Roles roles = ctx.getMethod().getAnnotation(Roles.class);

        Set<SecurityViolation> result = new HashSet<SecurityViolation>();
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
        logger.debug("Validation OK: " + ok);
        if (!ok) {
            final String msg = secMessage.roleMissing();
            result.add(new SecurityViolation() {
                @Override
                public String getReason() {
                    return msg;
                }
            });
            event.fire(new ExceptionToCatchEvent(new AccessDeniedException(result)));
        }

        return ctx.proceed();
    }
}
