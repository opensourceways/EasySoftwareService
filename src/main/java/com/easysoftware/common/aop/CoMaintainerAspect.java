package com.easysoftware.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.easysoftware.common.account.UserPermission;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ResultUtil;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class CoMaintainerAspect {
    /**
     * Logger for CoMaintainerAspect.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CoMaintainerAspect.class);

    /**
     * Autowired UserPermission for get user permission.
     */
    @Autowired
    private UserPermission userPermission;

    /**
     * Autowired HttpServletRequest for handling HTTP request information.
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * Advice method called before a method with CoMaintainerPermission, and authentication.
     * @param joinPoint    The JoinPoint representing the intercepted method.
     * @throws Throwable   if an error occurs during method execution, or authentication fail.
     * @return Business processing results.
     */
    @Around("@annotation(com.easysoftware.common.annotation.CoMaintainerPermission)")
    public Object around(final ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            String repo = request.getParameter("repo");

            /* Check if the user has repo permission */
            boolean permissionFlag = userPermission.checkUserRepoPermission(repo);

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
