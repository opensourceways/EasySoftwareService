package com.easysoftware.common.account;

import cn.dev33.satoken.exception.NotLoginException;
import com.easysoftware.common.constant.HttpConstant;
import com.easysoftware.common.constant.RedisConstant;
import com.easysoftware.common.exception.HttpRequestException;
import com.easysoftware.common.utils.HttpClientUtil;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.redis.RedisGateway;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class UserPermission {

    /**
     * Logger for UserPermission.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserPermission.class);

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
     * Value injected for the user info API.
     */
    @Value("${oneid.userInfoApi}")
    private String userInfoApi;

    /**
     * Value injected for the user repos API.
     */
    @Value("${oneid.userReposApi}")
    private String userReposApi;

    /**
     * Value injected for the cookie token name.
     */
    @Value("${cookie.token.name}")
    private String cookieTokenName;

    /**
     * RedisGateway instance for interacting with Redis.
     */
    @Resource
    private RedisGateway redisGateway;

    /**
     * check user permission.
     * @param  requirePermissions required user Permissions.
     * @return Permission matching results.
     */
    public boolean checkUserPermission(String[] requirePermissions) {
        /* 访问权限要求为空 */
        if (Objects.isNull(requirePermissions) || 0 == requirePermissions.length) {
            return true;
        }

        /* 获取客户权限 */
        HashSet<String> permissionSet = this.getPermissionList();
        if (Objects.isNull(permissionSet) || permissionSet.isEmpty()) {
            return false;
        }

        /* 检查客户权限是否满足访问权限 */
        return Arrays.stream(requirePermissions).anyMatch(permissionSet::contains);
    }

    /**
     * Check if user has repo permission.
     * @param  repo repo required user permission.
     * @return Permission matching results.
     */
    public boolean checkUserRepoPermission(String repo) {
        HashSet<String> repoSet = this.getUserRepoList();
        if (Objects.isNull(repoSet) || repoSet.isEmpty()) {
            return false;
        }

        /* Check if user has repo permission */
        return repoSet.contains(repo);
    }

    /**
     * Check if user has maintainer permission.
     * @return Permission matching results.
     */
    public boolean checkUserMaintainerPermission() {
        HashSet<String> repoSet = this.getUserRepoList();
        if (Objects.isNull(repoSet) || repoSet.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Get user login name by user token and manage token.
     * @return login name.
     */
    public String getUserLogin() {
        String userToken = getUserToken();
        String manageToken = getManageToken();

        // 使用userToken、manageToken查询用户信息
        Cookie cookie = getCookie(cookieTokenName);
        String response = HttpClientUtil.getHttpClient(userInfoApi, manageToken, userToken, cookie.getValue());
        JsonNode resJson = ObjectMapperUtil.toJsonNode(response);

        String resCode = resJson.path("code").asText();
        if (!"200".equals(resCode)) {
            LOGGER.error("query user login name failed");
            throw new HttpRequestException("query user login name failed");
        }
        String loginName = null;
        JsonNode identities = resJson.path("data").path("identities");
        for (JsonNode identity : identities) {
            if (identity.has("identity") && identity.get("identity").asText().equalsIgnoreCase("gitee")) {
                loginName = identity.get("login_name").asText();
            }
        }
        return loginName;
    }

    /**
     * Get user name by user token and manage token.
     * @return user name.
     */
    public String getUserName() {
        String userToken = getUserToken();
        String manageToken = getManageToken();

        // 使用userToken、manageToken查询用户信息
        Cookie cookie = getCookie(cookieTokenName);
        String response = HttpClientUtil.getHttpClient(userInfoApi, manageToken, userToken, cookie.getValue());
        JsonNode resJson = ObjectMapperUtil.toJsonNode(response);

        String resCode = resJson.path("code").asText();
        if (!"200".equals(resCode)) {
            LOGGER.error("query user login name failed");
            throw new HttpRequestException("query user login name failed");
        }
        String userName = resJson.path("data").path("username").asText();
        return userName;
    }

    /**
     * Get user repos.
     * @return Collection of repos.
     */
    public HashSet<String> getUserRepoList() {
        String login = getUserLogin();
        if (login == null) {
            LOGGER.info("user login name is null");
            return new HashSet<String>();
        }
        String response = HttpClientUtil.getHttpClient(String.format(userReposApi, login), null, null, null);
        JsonNode resJson = ObjectMapperUtil.toJsonNode(response);

        String resCode = resJson.path("code").asText();

        if (!"200".equals(resCode)) {
            LOGGER.error("query user repos failed");
            throw new HttpRequestException("query user repos failed");
        }
        HashSet<String> repoSet = new HashSet<>();
        JsonNode repos = resJson.get("data");
        for (JsonNode repo : repos) {
            repoSet.add(repo.asText());
        }

        return repoSet;
    }

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

        String resCode = resJson.path("code").asText();
        // 查询权限失败
        if (!"200".equals(resCode)) {
            LOGGER.error("query user permissions failed");
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
     *
     * @return manage token.
     */
    private String getManageToken() {
        String token = redisGateway.get(RedisConstant.MANAGER_TOKEN);
        if (token == null) {
            String response = HttpClientUtil.postHttpClient(manageTokenApi, manageApiBody);
            JsonNode resJson = ObjectMapperUtil.toJsonNode(response);
            if (resJson.path("status").asInt() == 200) {
                token = resJson.get("token").asText();
                long expire = resJson.path("token_expire").asLong();
                long tokenExpire = expire == 0 ? RedisConstant.TOKEN_EXPIRE : expire - RedisConstant.TOKEN_EXPIRE;
                redisGateway.setWithExpire(RedisConstant.MANAGER_TOKEN, token, tokenExpire, TimeUnit.SECONDS);
            }
        }
        return token;
    }

    /**
     * Get user token.
     * @return user token.
     */
    private String getUserToken() {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(servletRequestAttributes)) {

            LOGGER.error("Missing HTTP parameter");
            throw new HttpRequestException("Missing HTTP parameter");
        }
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();

        String userToken = httpServletRequest.getHeader(HttpConstant.TOKEN);
        if (null == userToken) {
            LOGGER.error("Missing user token");
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
            LOGGER.error("Missing HTTP parameter");
            throw new HttpRequestException("Missing HTTP parameter");
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
            LOGGER.error("Missing valid cookies");
            throw new NotLoginException("user token expired or no login", "", "");
        }
        return cookie;
    }

}
