package com.easysoftware.common.aop;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.easysoftware.common.entity.MessageCode;

import com.easysoftware.common.utils.ClientUtil;
import com.easysoftware.common.utils.ResultUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

@Aspect
@Component

public class RequestLimitRedisAspect {
    private static final Logger logger = LoggerFactory.getLogger(RequestLimitRedisAspect.class);

    @Autowired
    RedisTemplate  redisTemplate;

    @Value("${dos-global.rejectPeriod}") 
    long rejectPeriod;

    @Value("${dos-global.rejectCount}") 
    long rejectCount;

    // 切点
    @Pointcut("@annotation(requestLimit)")
    public void controllerAspect(RequestLimitRedis requestLimit) {}

    @Around("controllerAspect(requestLimit)")
    public Object before(ProceedingJoinPoint joinPoint, RequestLimitRedis requestLimit) throws Throwable {
        
        long period = rejectPeriod;
        long limitCount = rejectCount;
        
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 获取url
        String ip = ClientUtil.getClientIpAddress(request);
        String uri = request.getRequestURI() ; 
        String key = "req_limit:".concat(uri).concat(ip);


        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        long currentMs = System.currentTimeMillis();
        zSetOperations.add(key, currentMs, currentMs);

   
        redisTemplate.expire(key, period, TimeUnit.SECONDS);

        // remove the value that out of current window
        zSetOperations.removeRangeByScore(key, 0, currentMs - period * 1000);

        // 检查访问次数
        Long count = zSetOperations.zCard(key);
   
        if (count > limitCount) {
            // 审计日志
            logger.error("接口拦截：{}，请求超过限制频率【{}次/{}s】,IP：{}", uri, limitCount, period, ip);
            return ResultUtil.fail(HttpStatus.TOO_MANY_REQUESTS, MessageCode.EC00010);
        }

  
        return  joinPoint.proceed();
    }

}

