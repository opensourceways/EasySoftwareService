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
    // 限制时间 单位：秒(默认值：30s）  
    long period() default 30;  
  
    // 允许请求的次数(默认值：5次）  
    long count() default 5;  
}
