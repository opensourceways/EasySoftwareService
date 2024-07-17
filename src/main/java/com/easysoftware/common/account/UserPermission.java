package com.easysoftware.common.account;

import cn.dev33.satoken.exception.NotLoginException;
import com.easysoftware.common.constant.HttpConstant;
import com.easysoftware.common.exception.HttpRequestException;
import com.easysoftware.common.utils.HttpClientUtil;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashSet;
import java.util.Objects;

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
     * Get user permission by user token and manage token.
     * @return Collection of user permissions.
     */
    public HashSet<String> getPermissionList() {
        // 使用获取userToken
        String userToken = getUserToken();

        // 获取oneid manage token
        String manageToken = getManageToken();

        // 使用userToke、manageToken查询用户权限
        String response = HttpClientUtil.getHttpClient(permissionApi, manageToken, userToken, userToken);
        JsonNode resJson = ObjectMapperUtil.toJsonNode(response);

        String resStaus = resJson.get("status").asText();
        // 查询权限失败
        if (!resStaus.equals("200")) {
            throw new HttpRequestException("query oneid failed");
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

}
