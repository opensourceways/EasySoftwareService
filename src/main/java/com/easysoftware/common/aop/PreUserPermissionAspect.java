package com.easysoftware.common.aop;

import com.easysoftware.common.account.UserPermission;
import com.easysoftware.common.annotation.PreUserPermission;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ResultUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class PreUserPermissionAspect {
    /**
     * Logger for PreUserPermissionAspect.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PreUserPermissionAspect.class);

    /**
     * Autowired UserPermission for get user permission.
     */
    @Autowired
    private UserPermission userPermission;

    /**
     * Advice method called before a method with PreUserPermission, and authentication.
     * @param joinPoint    The JoinPoint representing the intercepted method.
     * @throws Throwable   if an error occurs during method execution, or authentication fail.
     * @return Business processing results.
     */
    @Around("@annotation(com.easysoftware.common.annotation.PreUserPermission)")
    public Object around(final ProceedingJoinPoint joinPoint) throws Throwable {
        /* 用户权限检查 */
        try {
            /* 获取PreUserPermission注解参数 */
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            PreUserPermission preUserPermission = method.getAnnotation(PreUserPermission.class);
            String[] paramValues = preUserPermission.value();

            /* 检查客户权限是否满足访问权限 */
            boolean permissionFlag = userPermission.checkUserPermission(paramValues);

            if (!permissionFlag) {
                LOGGER.error("Insufficient permissions");
                return  ResultUtil.fail(HttpStatus.FORBIDDEN, MessageCode.EC00019);
            }
        } catch (Exception e) {
            LOGGER.error("Authentication exception");
            return  ResultUtil.fail(HttpStatus.UNAUTHORIZED, MessageCode.EC00020);
        }

        /* 业务处理 */
        return joinPoint.proceed();
    }
}
