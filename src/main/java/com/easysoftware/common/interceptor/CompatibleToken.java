package com.easysoftware.common.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CompatibleToken {
    /**
     * Indicates whether the element is required.
     *
     * @return True if the element is required, false otherwise
     */
    boolean required() default true;
}
