package com.easysoftware.common.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OneidToken {
    /**
     * Indicates whether the field is required.
     *
     * @return true if the field is required, false otherwise.
     */
    boolean required() default true;
}
