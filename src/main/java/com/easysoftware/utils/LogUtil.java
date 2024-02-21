package com.easysoftware.utils;

import com.easysoftware.vo.ManagementLog;
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
import java.util.HashMap;

public class LogUtil {
    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public static void managementOperate(JoinPoint joinPoint, HttpServletRequest request, HttpServletResponse response,
            Object returnObject) {
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
            ResponseEntity responseEntity = (ResponseEntity) returnObject;
            log.setStatus(responseEntity.getStatusCodeValue());
            if (responseEntity.getBody() instanceof HashMap) {
                HashMap<String, Object> body = (HashMap) responseEntity.getBody();
                Object msg = (body.get("msg") == null) ? body.get("message") : body.get("msg");
                log.setMessage((msg == null) ? "" : msg.toString());
            }
        }
        log.setOperator("");

        String jsonLog = objectMapper.writeValueAsString(log);
        logger.info("operationLog:{}", jsonLog);
    }

}