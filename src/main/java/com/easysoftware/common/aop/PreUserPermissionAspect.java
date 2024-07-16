package com.easysoftware.common.aop;

import com.easysoftware.common.account.UserPermission;
import com.easysoftware.common.annotation.PreUserPermission;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;

@Aspect
@Component
public class PreUserPermissionAspect {

    @Autowired
    UserPermission userPermission;

    /**
     * Defines the pointcut for methods in specific packages.
     */
    @Pointcut("@annotation(com.easysoftware.common.annotation.PreUserPermission)")
    public void pointcut() {}

    @Before("pointcut()")
    public void before(final JoinPoint joinPoint) throws Throwable {
        /* 获取PreUserPermission注解参数 */
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        PreUserPermission preUserPermission = method.getAnnotation(PreUserPermission.class);
        String[] paramValues = preUserPermission.value();

        /* 未指定参数 */
        if (Objects.isNull(paramValues) || 0 == paramValues.length) {
            return ;
        }

        /* 获取客户权限，检查 */
        HashSet<String> permissionSet = userPermission.getPermissionList();
        for (String item:paramValues) {
            if (permissionSet.contains(item)) {
                return ;
            }
        }

        throw  new Exception("您无权限访问");
    }
}
