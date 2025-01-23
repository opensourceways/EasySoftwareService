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

package com.easysoftware.adapter.query;

import com.easysoftware.common.account.UerPermissionDef;
import com.easysoftware.common.account.UserPermission;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easysoftware.application.applicationversion.ApplicationVersionService;
import com.easysoftware.application.applicationversion.dto.ApplicationColumnSearchCondition;
import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
import com.easysoftware.common.aop.RequestLimitRedis;

import jakarta.validation.Valid;

import java.util.HashMap;

@RestController
@RequestMapping("/appVersion")
public class ApplicationVersionQueryAdapter {
    /**
     * Logger for ApplicationVersionQueryAdapter.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationVersionQueryAdapter.class);

    /**
     * Autowired service for handling application version-related operations.
     */
    @Autowired
    private ApplicationVersionService appVersionService;

    /**
     * Autowired UserPermission for check user permission.
     */
    @Autowired
    private UserPermission userPermission;

    /**
     * Define current functional permissions.
     */
    private static final String[] REQUIRE_PERMISSIONS = {UerPermissionDef.USER_PERMISSION_READ};

    /**
     * Endpoint to search for application versions based on the provided search
     * condition.
     *
     * @param condition The search condition for querying application versions.
     * @return ResponseEntity<Object>.
     */
    @GetMapping()
    @RequestLimitRedis()
    public ResponseEntity<Object> searchAppVersion(@Valid final ApplicationVersionSearchCondition condition) {
        // 检查会话权限
        return appVersionService.searchAppVersion(condition);
    }

    /**
     * Endpoint to search for application versions based on the provided search
     * condition.
     *
     * @param condition The search condition for querying application versions.
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/column")
    @RequestLimitRedis()
    public ResponseEntity<Object> searchAppVerColumn(@Valid final ApplicationColumnSearchCondition condition) {
        // 检查会话权限
        return appVersionService.searchAppVerColumn(condition);
    }

    /**
     * Check if the user has permission to access.
     *
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/permission")
    @RequestLimitRedis()
    public ResponseEntity<Object> checkPermission() {
        HashMap<String, Boolean> result = new HashMap<>();
        try {
            /* 检查用户权限 */
            boolean permissionFlag = userPermission.checkUserPermission(REQUIRE_PERMISSIONS);

            if (permissionFlag) {
                result.put("allow_access", Boolean.TRUE);
            } else {
                result.put("allow_access", Boolean.FALSE);
            }
            return  ResultUtil.success(HttpStatus.OK, result);
        } catch (Exception e) {
            LOGGER.error("Authentication exception");
            return  ResultUtil.fail(HttpStatus.UNAUTHORIZED, MessageCode.EC00020);
        }
    }
}
