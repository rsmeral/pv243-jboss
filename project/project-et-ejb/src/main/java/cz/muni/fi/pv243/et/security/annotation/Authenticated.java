package cz.muni.fi.pv243.et.security.annotation;

import cz.muni.fi.pv243.et.security.AuthenticationAccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.annotation.Secured;

import javax.enterprise.inject.Stereotype;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Stereotype
@Secured(AuthenticationAccessDecisionVoter.class)
public @interface Authenticated {
}
