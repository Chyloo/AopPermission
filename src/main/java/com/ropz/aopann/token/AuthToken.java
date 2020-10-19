package com.ropz.aopann.token;

import java.lang.annotation.*;

/**
 * @author ropz
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthToken {
    String roleName() default "";
}
