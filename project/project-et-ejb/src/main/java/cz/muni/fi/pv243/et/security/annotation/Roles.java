package cz.muni.fi.pv243.et.security.annotation;

import cz.muni.fi.pv243.et.model.PersonRole;
import cz.muni.fi.pv243.et.security.RoleAccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.annotation.Secured;

import javax.enterprise.inject.Stereotype;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Stereotype
@Secured(RoleAccessDecisionVoter.class)
public @interface Roles {

    boolean anyRole() default true;

    PersonRole[] value() default {};
}
