package cz.muni.fi.pv243.et.security.annotation;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@InterceptorBinding
public @interface Authenticated {
}
