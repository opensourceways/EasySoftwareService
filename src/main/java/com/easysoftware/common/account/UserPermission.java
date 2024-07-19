package com.easysoftware.common.account;

import cn.dev33.satoken.exception.NotLoginException;
import com.easysoftware.common.constant.HttpConstant;
import com.easysoftware.common.exception.HttpRequestException;
import com.easysoftware.common.utils.HttpClientUtil;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserPermission {

    /**
     * Value injected for the manage token API.
     */
    @Value("${oneid.manage.tokenApi}")
    private String manageTokenApi;

    /**
     * Value injected for the manage API body.
     */
    @Value("${oneid.manage.apiBody}")
    private String manageApiBody;

    /**
     * Value injected for the permission API.
     */
    @Value("${oneid.permissionApi}")
    private String permissionApi;

    /**
     * Value injected for the cookie token name.
     */
    @Value("${cookie.token.name}")
    private String cookieTokenName;

    /**
     * Get user permission by user token and manage token.
     * @return Collection of user permissions.
     */
    public HashSet<String> getPermissionList() {
        // 使用获取userToken
        String userToken = getUserToken();

        // 获取oneid manage token
        String manageToken = getManageToken();

        // 使用userToke、manageToken查询用户权限
        Cookie cookie = getCookie(cookieTokenName);
        String response = HttpClientUtil.getHttpClient(permissionApi, manageToken, userToken, cookie.getValue());
        JsonNode resJson = ObjectMapperUtil.toJsonNode(response);

        String resCode = resJson.get("code").asText();
        // 查询权限失败
        if (!"200".equals(resCode)) {
            throw new HttpRequestException("query user permissions failed");
        }

        // 空权限账户
        if (!resJson.has("data")) {
            return new HashSet<String>();
        }

        // 设置权限
        JsonNode permissions = resJson.get("data").get("permissions");
        HashSet<String> permissionSet = new HashSet<>();
        for (JsonNode per : permissions) {
            permissionSet.add(per.asText());
        }

        return permissionSet;
    }

    /**
     * Get manage token.
     * @return manage token.
     */
    private String getManageToken() {
        String response = HttpClientUtil.postHttpClient(manageTokenApi, manageApiBody);
        JsonNode resJson = ObjectMapperUtil.toJsonNode(response);
        return resJson.get("token").asText();
    }

    /**
     * Get user token.
     * @return user token.
     */
    private String getUserToken() {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(servletRequestAttributes)) {
            throw new HttpRequestException("http request content error");
        }
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();

        String userToken = httpServletRequest.getHeader(HttpConstant.TOKEN);
        if (null == userToken) {
            throw new NotLoginException("user token expired or no login", "", "");
        }

        return userToken;
    }

    /**
     * Get a cookie from the HttpServletRequest.
     *
     * @param   cookieName  The name of the cookie to be obtained.
     * @return  cookie .
     */
    private Cookie getCookie(String cookieName) {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(servletRequestAttributes)) {
            throw new HttpRequestException("http request content error");
        }

        Cookie[] cookies = servletRequestAttributes.getRequest().getCookies();
        Cookie cookie = null;
        if (null != cookies) {
            // 获取cookie中的token
            Optional<Cookie> first = Arrays.stream(cookies).filter(c -> cookieName.equals(c.getName()))
                    .findFirst();
            if (first.isPresent()) {
                cookie = first.get();
            }
        }

        if (null == cookie) {
            throw new NotLoginException("user token expired or no login", "", "");
        }
        return cookie;
    }

}
