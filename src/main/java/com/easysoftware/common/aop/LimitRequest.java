package com.easysoftware.common.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LimitRequest {
    /**
     * Specifies the default call time, set to 1.
     *
     * @return The default call time value.
     */
    int callTime() default 1;

    /**
     * Specifies the default call count, set to 10.
     *
     * @return The default call count value.
     */
    int callCount() default 10;

}
