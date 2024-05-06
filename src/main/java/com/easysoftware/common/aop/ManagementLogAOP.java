package com.easysoftware.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.easysoftware.common.utils.LogUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Aspect
@Component
public class ManagementLogAOP {

    /**
     * Autowired HttpServletRequest for handling HTTP request information.
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * Autowired HttpServletResponse for handling HTTP response information.
     */
    @Autowired
    private HttpServletResponse response;

    /**
     * Defines the pointcut for methods in specific packages.
     */
    @Pointcut("execution(* com.easysoftware.adapter.query.*.*(..)) || execution(* com.easysoftware.adapter.execute.*.*(..))")
    public void pointcut() {
    }

    /**
     * Advice method called after a method in the specified packages successfully returns.
     *
     * @param joinPoint    The JoinPoint representing the intercepted method.
     * @param returnObject The object returned by the intercepted method.
     */
    @AfterReturning(pointcut = "pointcut()", returning = "returnObject")
    public void afterReturning(final JoinPoint joinPoint, final Object returnObject) {
        LogUtil.managementOperate(joinPoint, request, response, returnObject);
    }

}

