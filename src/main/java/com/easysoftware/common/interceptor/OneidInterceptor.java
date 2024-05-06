package com.easysoftware.common.interceptor;

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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OneidInterceptor implements HandlerInterceptor {

    /**
     * Value injected for the cookie token name.
     */
    @Value("${cookie.token.name}")
    private String cookieTokenName;

    /**
     * Value injected for the permission API.
     */
    @Value("${oneid.permissionApi}")
    private String permissionApi;

    /**
     * Value injected for the manage API body.
     */
    @Value("${oneid.manage.apiBody}")
    private String manageApiBody;

    /**
     * Value injected for the manage token API.
     */
    @Value("${oneid.manage.tokenApi}")
    private String manageTokenApi;

    /**
     * Logger instance for OneidInterceptor.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OneidInterceptor.class);


    /**
     * Method invoked before the actual handler is executed.
     *
     * @param httpServletRequest  The request being handled
     * @param httpServletResponse The response being generated
     * @param object              The handler object to handle
     * @return True if the execution chain should proceed with the next interceptor or the handler itself;
     * false if the interceptor has already taken care of the response itself
     * @throws Exception in case of errors
     */
    @Override
    public boolean preHandle(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse,
                             final Object object) throws Exception {
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

    /**
     * Method called after the handler is executed.
     *
     * @param httpServletRequest  The request being handled
     * @param httpServletResponse The response being generated
     * @param o                   The handler object that was used
     * @param modelAndView        The ModelAndView object that was returned by the handler
     * @throws Exception in case of errors
     */
    @Override
    public void postHandle(final HttpServletRequest httpServletRequest,
                           final HttpServletResponse httpServletResponse,
                           final Object o, final ModelAndView modelAndView) throws Exception {

    }

    /**
     * Method called after the complete request has finished being handled.
     *
     * @param httpServletRequest  The request being handled
     * @param httpServletResponse The response being generated
     * @param o                   The handler object that was used
     * @param e                   Exception thrown during handler execution, if any
     * @throws Exception in case of errors
     */
    @Override
    public void afterCompletion(final HttpServletRequest httpServletRequest,
                                final HttpServletResponse httpServletResponse,
                                final Object o, final Exception e) throws Exception {
    }

    /**
     * Verifies the user by checking the provided token.
     *
     * @param compatibleToken    The compatible token object
     * @param httpServletRequest The HTTP servlet request
     * @param tokenCookie        The token cookie
     * @param userToken          The user token to verify
     * @return A string representing the verification status
     */
    private String verifyUser(final CompatibleToken compatibleToken, final HttpServletRequest httpServletRequest,
                              final Cookie tokenCookie, final String userToken) {
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

    /**
     * Retrieves the user permissions based on the provided token and request information.
     *
     * @param httpServletRequest The HTTP servlet request
     * @param tokenCookie        The token cookie
     * @param userToken          The user token
     * @return A list of user permissions
     */
    @SneakyThrows
    private List<String> getUserPermission(final HttpServletRequest httpServletRequest, final Cookie tokenCookie, final String userToken) {
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

    /**
     * Retrieves the management token.
     *
     * @return The management token
     */
    @SneakyThrows
    private String getManageToken() {
        String response = HttpClientUtil.postHttpClient(manageTokenApi, manageApiBody);
        JsonNode resJson = ObjectMapperUtil.toJsonNode(response);
        return resJson.get("token").asText();
    }

    /**
     * Verifies and retrieves a cookie from the HttpServletRequest.
     *
     * @param httpServletRequest The HTTP servlet request
     * @return The verified Cookie object
     */
    private Cookie verifyCookie(final HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        Cookie cookie = null;
        if (cookies != null) {
            // 获取cookie中的token
            Optional<Cookie> first = Arrays.stream(cookies).filter(c -> cookieTokenName.equals(c.getName()))
                    .findFirst();
            if (first.isPresent()) {
                cookie = first.get();
            }
        }
        return cookie;
    }

}