/* Copyright (c) 2024 openEuler Community
 EasySoftware is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

package com.easysoftware.common.account;

import java.util.ArrayList;
import java.util.List;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.easysoftware.common.exception.HttpRequestException;
import com.easysoftware.common.utils.HttpClientUtil;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.redis.RedisGateway;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class StpInterfaceImpl implements StpInterface {
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
     * Autowired redisGateway.
     */
    @Autowired
    private RedisGateway redisGateway;

    /**
     * Get permission from oneid.
     *
     * @param loginId   the login id of user.
     * @param loginType the type of login.
     * @return ResponseEntity<Object>.
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {

        // 获取oneid manage token
        String manageToken = getManageToken();

        // 使用loginid 从redis中获取userToken

        if (!redisGateway.hasKey((String) loginId)) {
            throw new NotLoginException("user token expired", "", "");
        }
        String userToken = redisGateway.get((String) loginId);

        // 使用userToken manageToken查询用户权限
        String response = HttpClientUtil.getHttpClient(permissionApi, manageToken, userToken, userToken);
        JsonNode resJson = ObjectMapperUtil.toJsonNode(response);

        String resStaus = resJson.get("status").asText();
        // 查询权限失败
        if (!resStaus.equals("200")) {
            throw new HttpRequestException("query oneid failed");
        }

        // 空权限账户
        if (!resJson.has("data")) {
            return new ArrayList<String>();
        }

        // 设置权限
        JsonNode permissions = resJson.get("data").get("permissions");
        List<String> list = new ArrayList<String>();
        for (JsonNode per : permissions) {
            list.add(per.asText());
        }
        return list;
    }

    /**
     * Get role list from user.
     *
     * @param loginId   the login id of user.
     * @param loginType the type of login.
     * @return ResponseEntity<Object>.
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> list = new ArrayList<String>();
        return list;
    }

    private String getManageToken() {
        String response = HttpClientUtil.postHttpClient(manageTokenApi, manageApiBody);
        JsonNode resJson = ObjectMapperUtil.toJsonNode(response);
        return resJson.get("token").asText();
    }

}
