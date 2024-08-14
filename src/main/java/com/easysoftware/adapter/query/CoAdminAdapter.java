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

import java.util.HashMap;

import com.easysoftware.application.apply.ApplyService;
import com.easysoftware.application.applyform.ApplyFormService;
import com.easysoftware.application.applyform.dto.ProcessApply;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.easysoftware.common.account.UerPermissionDef;
import com.easysoftware.common.account.UserPermission;
import com.easysoftware.common.annotation.PreUserPermission;
import com.easysoftware.common.aop.RequestLimitRedis;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ResultUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/collaboration/admin")
public class CoAdminAdapter {
    /**
     * Logger for ApplicationVersionQueryAdapter.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CoAdminAdapter.class);

    /**
     * Define current functional permissions.
     */
    private static final String[] REQUIRE_PERMISSIONS = {UerPermissionDef.COLLABORATION_PERMISSION_ADMIN };

    /**
     * Autowired UserPermission for check user permission.
     */
    @Autowired
    private UserPermission userPermission;

    /**
     * Autowired ApplyService for provide apply service.
     */
    @Autowired
    private ApplyService applyService;

    /**
     * Autowired service for search applyForm.
     */
    @Autowired
    private ApplyFormService applyFormService;

    /**
     * Endpoint to search for repos based on the provided search
     * condition.
     *
     * @param repo The search condition for querying repos.
     * @return ResponseEntity<Object>.
     */
    @GetMapping()
    @RequestLimitRedis()
    @PreUserPermission(UerPermissionDef.COLLABORATION_PERMISSION_ADMIN)
    public ResponseEntity<Object> queryRepos(@RequestParam(value = "repo") String repo) {
        return ResultUtil.success(HttpStatus.OK, "success");
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
            boolean permissionFlag = userPermission.checkUserPermission(REQUIRE_PERMISSIONS);

            if (permissionFlag) {
                result.put("allow_access", Boolean.TRUE);
            } else {
                result.put("allow_access", Boolean.FALSE);
            }
            return ResultUtil.success(HttpStatus.OK, result);
        } catch (Exception e) {
            LOGGER.error("Authentication exception - {}", e.getMessage());
            return ResultUtil.fail(HttpStatus.UNAUTHORIZED, MessageCode.EC00020);
        }
    }

    /**
     * get apply handle records by appid.
     *
     * @param applyId The handle form content id.
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/query/records")
    @RequestLimitRedis()
    @PreUserPermission(UerPermissionDef.COLLABORATION_PERMISSION_ADMIN)
    public ResponseEntity<Object> getApply(@RequestParam(value = "applyId") Long applyId) {
        return applyService.queryApplyHandleRecords(applyId);
    }

    /**
     * process apply by applyid.
     *
     * @param processApply process apply.
     * @return ResponseEntity<Object>.
     */
    @PostMapping("/process")
    @RequestLimitRedis()
    @PreUserPermission(UerPermissionDef.COLLABORATION_PERMISSION_ADMIN)
    public ResponseEntity<Object> processApply(@Valid @RequestBody ProcessApply processApply) {
        return applyFormService.processApply(processApply);
    }

}
