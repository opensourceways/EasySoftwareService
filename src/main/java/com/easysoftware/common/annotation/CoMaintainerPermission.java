package com.easysoftware.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Inherited;
import java.lang.annotation.Documented;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CoMaintainerPermission {

    /**
     * Specifies the number of allowed requests (default value: 5).
     *
     * @return  Default to empty.
     */
    String[] value() default {};
}
