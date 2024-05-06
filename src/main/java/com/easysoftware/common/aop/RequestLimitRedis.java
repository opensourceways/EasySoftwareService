package com.easysoftware.common.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLimitRedis {
    /**
     * Specifies the time limit in seconds (default value: 30s).
     *
     * @return The time limit in seconds.
     */
    long period() default 30;

    /**
     * Specifies the number of allowed requests (default value: 5).
     *
     * @return The number of allowed requests.
     */
    long count() default 5;

}
