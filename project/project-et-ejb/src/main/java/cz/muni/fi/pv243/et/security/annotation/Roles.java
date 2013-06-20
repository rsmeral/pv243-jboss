package cz.muni.fi.pv243.et.security.annotation;

import cz.muni.fi.pv243.et.model.PersonRole;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@InterceptorBinding
public @interface Roles {

    @Nonbinding
    boolean anyRole() default true;

    @Nonbinding
    PersonRole[] value() default {};
}
