package cz.muni.fi.pv243.et.security;

import cz.muni.fi.pv243.et.message.SecurityMessage;
import cz.muni.fi.pv243.et.security.annotation.Authenticated;
import org.apache.deltaspike.core.api.exception.control.event.ExceptionToCatchEvent;
import org.apache.deltaspike.security.api.authorization.AccessDeniedException;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;
import org.picketlink.Identity;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.HashSet;
import java.util.Set;

@Authenticated
@Interceptor
public class AuthBasedAuthorizationInterceptor {

    @Inject
    private SecurityMessage secMessage;

    @Inject
    private Identity identity;

    @Inject
    private Event<ExceptionToCatchEvent> event;

    @AroundInvoke
    public Object authorizeBasedOnAuthentication(InvocationContext ctx) throws Exception {
        Set<SecurityViolation> violations = new HashSet<SecurityViolation>();
        if (!identity.isLoggedIn()) {
            final String msg = secMessage.notAuthenticated();
            violations.add(new SecurityViolation() {
                @Override
                public String getReason() {
                    return msg;
                }
            });
            event.fire(new ExceptionToCatchEvent(new AccessDeniedException(violations)));
        }

        return ctx.proceed();
    }
}
