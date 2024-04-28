package com.easysoftware.common.interceptor;

import java.lang.reflect.Method;
import java.util.*;

import com.easysoftware.common.constant.HttpConstant;
import com.easysoftware.common.exception.AuthException;
import com.easysoftware.common.utils.HttpClientUtil;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class OneidInterceptor implements HandlerInterceptor {

    @Value("${cookie.token.name}")
    private String cookieTokenName;

    @Value("${oneid.permissionApi}")
    private String permissionApi;

    @Value("${oneid.manage.apiBody}")
    private String manageApiBody;

    @Value("${oneid.manage.tokenApi}")
    private String manageTokenApi;

    private static final Logger logger = LoggerFactory.getLogger(OneidInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Object object) throws Exception {
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        // 检查是否有用户权限的注解
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        if (!method.isAnnotationPresent(OneidToken.class) && !method.isAnnotationPresent(CompatibleToken.class)) {
            return true;
        }
        OneidToken oneidToken = method.getAnnotation(OneidToken.class);
        CompatibleToken compatibleToken = method.getAnnotation(CompatibleToken.class);
        if ((oneidToken == null || !oneidToken.required())
                && (compatibleToken == null || !compatibleToken.required())) {
            return true;
        }

        // 校验cookie
        Cookie tokenCookie = verifyCookie(httpServletRequest);
        if (tokenCookie == null) {
            throw new AuthException("unauthorized, missing cookie");
        }

        String userToken = httpServletRequest.getHeader(HttpConstant.TOKEN);
        if (userToken == null) {
            throw new AuthException("unauthorized, missing token");
        }

        // 校验查看版本兼容页面的权限
        if (compatibleToken != null && compatibleToken.required()) {
            String verifyUserMsg = verifyUser(compatibleToken, httpServletRequest, tokenCookie, userToken);
            if (!verifyUserMsg.equals("success")) {
                throw new AuthException(verifyUserMsg);
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Object o, Exception e) throws Exception {
    }

    /**
     * 校验用户操作权限
     */
    private String verifyUser(CompatibleToken compatibleToken, HttpServletRequest httpServletRequest,
            Cookie tokenCookie, String userToken) {
        if (compatibleToken != null && compatibleToken.required()) {
            List<String> pers = getUserPermission(httpServletRequest, tokenCookie, userToken);
            for (String per : pers) {
                if (per.equalsIgnoreCase("easysoftwareread")) {
                    return "success";
                }
            }
        }
        return "No permission";
    }

    @SneakyThrows
    private List<String> getUserPermission(HttpServletRequest httpServletRequest, Cookie tokenCookie, String userToken) {
        String token = getManageToken();
        String tokenCookieValue = tokenCookie.getValue();
        String response = HttpClientUtil.getHttpClient(permissionApi, token, userToken, tokenCookieValue);
        JsonNode resJson = ObjectMapperUtil.toJsonNode(response);
        if (!resJson.has("data")) {
            throw new AuthException(resJson.get("message").asText());
        }
        JsonNode permissions = resJson.get("data").get("permissions");
        List<String> list = new ArrayList<>();
        for (JsonNode per : permissions) {
            list.add(per.asText());
        }
        return list;
    }

    @SneakyThrows
    private String getManageToken() {
        String response = HttpClientUtil.postHttpClient(manageTokenApi, manageApiBody);
        JsonNode resJson = ObjectMapperUtil.toJsonNode(response);
        return resJson.get("token").asText();
    }

    /**
     * 获取包含存token的cookie
     *
     * @param httpServletRequest request
     * @return cookie
     */
    private Cookie verifyCookie(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        Cookie cookie = null;
        if (cookies != null) {
            // 获取cookie中的token
            Optional<Cookie> first = Arrays.stream(cookies).filter(c -> cookieTokenName.equals(c.getName()))
                    .findFirst();
            if (first.isPresent())
                cookie = first.get();
        }
        return cookie;
    }

}