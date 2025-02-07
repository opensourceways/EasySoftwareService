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
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.easysoftware.application.applyform.ApplyFormService;
import com.easysoftware.application.applyform.dto.ApplyFormSearchMaintainerCondition;
import com.easysoftware.application.applyform.dto.MyApply;
import com.easysoftware.application.collaboration.CoMaintainerService;
import com.easysoftware.application.collaboration.dto.PackageSearchCondition;
import com.easysoftware.common.account.UerPermissionDef;
import com.easysoftware.common.account.UserPermission;
import com.easysoftware.common.annotation.CoMaintainerPermission;
import com.easysoftware.common.annotation.CoUserRepoPermission;
import com.easysoftware.common.aop.RequestLimitRedis;
import com.easysoftware.common.constant.PackageConstant;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ResultUtil;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/collaboration/maintainer")
public class CoMaintainerAdapter {
    /**
     * Autowired service for handling package maintainer related operations.
     */
    @Autowired
    private CoMaintainerService coMaintainerService;

    /**
     * Logger for CoMaintainerAdapter.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CoMaintainerAdapter.class);

    /**
     * Define current functional permissions.
     */
    private static final String[] REQUIRE_PERMISSIONS = {UerPermissionDef.COLLABORATION_PERMISSION_ADMIN};

    /**
     * Autowired UserPermission for check user permission.
     */
    @Autowired
    private UserPermission userPermission;

    /**
     * Autowired service for search applyForm.
     */
    @Autowired
    private ApplyFormService applyFormService;

    /**
     * Endpoint to search for repos based on the provided search
     * condition.
     *
     * @param repo repo name.
     * @return ResponseEntity<Object>.
     */
    @GetMapping()
    @RequestLimitRedis()
    @CoMaintainerPermission()
    public ResponseEntity<Object> queryRepos(@RequestParam(value = "repo") String repo) {
        return ResultUtil.success(HttpStatus.OK, "success");
    }

    /**
     * Endpoint to search for repos based on the provided search
     * condition.
     *
     * @param condition The search condition for querying packages.
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/user/repos")
    @RequestLimitRedis()
    @CoMaintainerPermission()
    public ResponseEntity<Object> queryPackages(@Valid final PackageSearchCondition condition) {
        return coMaintainerService.queryPackages(condition);
    }

    /**
     * Check if the user has permission to access.
     *
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/permissions")
    @RequestLimitRedis()
    public ResponseEntity<Object> checkPermission() {
        HashMap<String, Object> result = new HashMap<>();
        HashSet<String> permissions = new HashSet<>();
        try {
            boolean permissionAdmin = userPermission.checkUserPermission(REQUIRE_PERMISSIONS);
            if (permissionAdmin) {
                permissions.add(PackageConstant.APPLY_FORM_ADMIN);
            } else {
                HashSet<String> permissionRepos = userPermission.getUserRepoList();
                if (permissionRepos.size() > 0) {
                    permissions.add(PackageConstant.APPLY_FORM_MAINTAINER);
                }
            }
            result.put("permissions", permissions);
            return ResultUtil.success(HttpStatus.OK, result);
        } catch (Exception e) {
            LOGGER.error("Authentication exception - {}", e.getMessage());
            return ResultUtil.fail(HttpStatus.UNAUTHORIZED, MessageCode.EC00020);
        }
    }

    /**
     * Query apply form based on the provided search condition.
     *
     * @param condition The search condition for querying apply form.
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/query/apply")
    @RequestLimitRedis()
    @CoMaintainerPermission()
    public ResponseEntity<Object> queryApplyFromByMaintainer(@Valid final
        ApplyFormSearchMaintainerCondition condition) {
        if (condition.getApplyIdString() != null) {
            condition.setApplyId(Long.valueOf(condition.getApplyIdString()));
        }
        return applyFormService.searchApplyFromByMaintainer(condition);
    }

    /**
     * Submit apply form based on the provided body.
     *
     * @param myApply The submit condition for querying apply form.
     * @param repo The submit condition for checking authority.
     * @return ResponseEntity<Object>.
     */
    @PostMapping("/apply")
    @RequestLimitRedis()
    @CoUserRepoPermission()
    public ResponseEntity<Object> submitMyApply(@Valid @RequestBody MyApply myApply,
    @RequestParam(value = "repo") String repo) {
        return applyFormService.submitMyApplyWithLimit(myApply);
    }

    /**
     * Revoke apply form based on the provided body.
     *
     * @param myApply The revoke condition for querying apply form.
     * @return ResponseEntity<Object>.
     */
    @PostMapping("/revoke")
    @RequestLimitRedis()
    @CoMaintainerPermission()
    public ResponseEntity<Object> revokeMyApply(@Valid @RequestBody MyApply myApply) {
        myApply.setApplyId(Long.valueOf(myApply.getApplyIdString()));
        return applyFormService.revokeMyApplyWithLimit(myApply);
    }

    /**
     * Update apply form based on the provided body.
     *
     * @param myApply The update condition for querying apply form.
     * @return ResponseEntity<Object>.
     */
    @PostMapping("/update")
    @RequestLimitRedis()
    @CoMaintainerPermission()
    public ResponseEntity<Object> updateMyApply(@Valid @RequestBody MyApply myApply) {
        myApply.setApplyId(Long.valueOf(myApply.getApplyIdString()));
        return applyFormService.updateMyApplyWithLimit(myApply);
    }

    /**
     * query repos maintainer applied to modify.
     *
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/apply/repos")
    @RequestLimitRedis()
    public ResponseEntity<Object> queryApplyRepos() {
        return applyFormService.queryApplyReposByMaintainer();
    }

    /**
     * query repos and sigs based on condition.
     *
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/query/repos")
    @RequestLimitRedis()
    @CoMaintainerPermission()
    public ResponseEntity<Object> queryRepoSigs() {
        return coMaintainerService.queryRepoSigs();
    }
}
