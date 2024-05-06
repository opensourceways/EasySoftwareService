package com.easysoftware.common.aop;

import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ResultUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class LimitRequestAspect {

    /**
     * ConcurrentHashMap to store call marks.
     */
    private final ConcurrentHashMap<String, CallMark> callMarkMap = new ConcurrentHashMap<>();

    /**
     * Pointcut method to filter methods annotated with LimitRequest.
     *
     * @param limitRequest The LimitRequest annotation.
     */
    @Pointcut("@annotation(limitRequest)")
    public void exudeService(final LimitRequest limitRequest) {
    }

    /**
     * Advice method that intercepts the method calls annotated with LimitRequest and enforces rate limiting.
     *
     * @param joinPoint    The ProceedingJoinPoint representing the intercepted method.
     * @param limitRequest The LimitRequest annotation.
     * @return The result of the intercepted method execution or a failure response for rate limiting.
     * @throws Throwable if an error occurs during method execution.
     */
    @Around(value = "exudeService(limitRequest)", argNames = "joinPoint,limitRequest")
    public Object before(final ProceedingJoinPoint joinPoint, final LimitRequest limitRequest) throws Throwable {
        if (!isAllowed(joinPoint.getSignature().getName(), limitRequest)) {
            return ResultUtil.fail(HttpStatus.TOO_MANY_REQUESTS, MessageCode.EC00010);
        }

        return joinPoint.proceed();
    }


    /**
     * Checks if the method is allowed based on rate limiting criteria specified in the LimitRequest annotation.
     *
     * @param methodName   The name of the method being checked.
     * @param limitRequest The LimitRequest annotation containing rate limiting criteria.
     * @return true if the method call is allowed based on rate limiting, false otherwise.
     */
    public boolean isAllowed(final String methodName, final LimitRequest limitRequest) {
        Duration timeWindow = Duration.ofSeconds(limitRequest.callTime());
        Instant now = Instant.now();
        if (callMarkMap.containsKey(methodName)) {
            CallMark callMark = callMarkMap.get(methodName);

            if (Duration.between(callMark.getLastCallTime(), now).compareTo(timeWindow) > 0) {
                callMark.setLastCallTime(now);
                callMark.setCallCount(0);
            }

            if (callMark.getCallCount() < limitRequest.callCount()) {
                callMark.setCallCount(callMark.getCallCount() + 1);
                callMarkMap.put(methodName, callMark);
                return true;
            }
            return false;

        } else {
            CallMark callMark = new CallMark();
            callMark.setLastCallTime(now);
            callMark.setCallCount(1);
            callMarkMap.put(methodName, callMark);
            return true;
        }
    }

}

