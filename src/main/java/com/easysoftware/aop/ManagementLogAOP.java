package com.easysoftware.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.easysoftware.utils.LogUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Aspect
@Component
public class ManagementLogAOP {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    //定义切点
    @Pointcut("execution(* com.easysoftware.adapter.*.*(..))")
    public void pointcut() {
    }

    @AfterReturning(pointcut = "pointcut()", returning = "returnObject")
    public void afterReturning(JoinPoint joinPoint, Object returnObject) {
        LogUtil.managementOperate(joinPoint, request, response, returnObject);
    }

}

