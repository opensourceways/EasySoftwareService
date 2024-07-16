package com.easysoftware.common.aop;

import com.easysoftware.common.account.UserPermission;
import com.easysoftware.common.annotation.PreUserPermission;
import com.easysoftware.common.exception.HttpRequestException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
    private UserPermission userPermission;

    @Before("@annotation(com.easysoftware.common.annotation.PreUserPermission)")
    public void before(final JoinPoint joinPoint) throws Throwable {
        /* 获取PreUserPermission注解参数 */
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        PreUserPermission preUserPermission = method.getAnnotation(PreUserPermission.class);
        String[] paramValues = preUserPermission.value();

        /* 方法使用注解，但未指定参数，默认无权限控制 */
        if (Objects.isNull(paramValues) || 0 == paramValues.length) {
            return ;
        }

        /* 获取客户权限 */
        HashSet<String> permissionSet = userPermission.getPermissionList();

        /* 检查客户权限是否满足访问权限 */
        for (String item:paramValues) {
            if (permissionSet.contains(item)) {
                return ;
            }
        }

        throw  new HttpRequestException("you do not have unauthorized access");
    }
}
