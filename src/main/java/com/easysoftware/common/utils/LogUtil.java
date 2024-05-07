package com.easysoftware.common.utils;

import com.easysoftware.common.aop.ManagementLog;
import com.easysoftware.common.entity.ResultVo;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LogUtil {


    // Private constructor to prevent instantiation of the utility class
    private LogUtil() {
        // private constructor to hide the implicit public one
        throw new AssertionError("LogUtil class cannot be instantiated.");
    }

    /**
     * Logger instance for LogUtil.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LogUtil.class);
    /**
     * ObjectMapper instance for JSON serialization and deserialization.
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Perform a management operation.
     *
     * @param joinPoint    The JoinPoint object
     * @param request      The HttpServletRequest object
     * @param response     The HttpServletResponse object
     * @param returnObject The object returned from the operation
     */
    @SneakyThrows
    public static void managementOperate(final JoinPoint joinPoint,
                                         final HttpServletRequest request,
                                         final HttpServletResponse response,
                                         final Object returnObject) {
        ManagementLog log = new ManagementLog();
        log.setType("OmOperate");

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.setTime(dateTime.format(formatter));

        log.setFunc(String.format("%s.%s", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName()));

        log.setRequestUrl(request.getRequestURI());
        log.setMethod(request.getMethod());

        log.setAppIP(ClientUtil.getClientIpAddress(request));

        if (returnObject instanceof ResponseEntity) {
            ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) returnObject;
            log.setStatus(responseEntity.getStatusCode().value());
            if (responseEntity.getBody() instanceof ResultVo) {
                ResultVo body = (ResultVo) responseEntity.getBody();
                Object msg = body.getMsg();
                log.setMessage((msg == null) ? "" : msg.toString());
            }
        }
        log.setOperator("");

        String jsonLog = OBJECT_MAPPER.writeValueAsString(log);
        LOGGER.info("operationLog:{}", jsonLog);
    }

}
