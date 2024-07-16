package com.easysoftware.common.annotation;

import java.lang.annotation.*;

/**
 * This annotation is used for authentication
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PreUserPermission {
    String[] value() default {} ;
}
