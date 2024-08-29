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

import com.easysoftware.application.apply.ApplyService;
import com.easysoftware.application.applyform.ApplyFormService;
import com.easysoftware.application.applyform.dto.ProcessApply;
import com.easysoftware.application.collaboration.CoAdminService;
import com.easysoftware.application.collaboration.dto.PackageSearchCondition;
import com.easysoftware.application.applyform.dto.ApplyFormSearchAdminCondition;
import com.easysoftware.application.applyform.dto.MyApply;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.easysoftware.common.account.UerPermissionDef;
import com.easysoftware.common.annotation.PreUserPermission;
import com.easysoftware.common.aop.RequestLimitRedis;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/collaboration/admin")
public class CoAdminAdapter {
    /**
     * Logger for ApplicationVersionQueryAdapter.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CoAdminAdapter.class);

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
     * Autowired service for CoAdminService.
     */
    @Autowired
    private CoAdminService coAdminService;

    /**
     * Endpoint to search for repos based on the provided search
     * condition.
     *
     * @param condition The search condition for querying repos.
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/user/repos")
    @RequestLimitRedis()
    @PreUserPermission(UerPermissionDef.COLLABORATION_PERMISSION_ADMIN)
    public ResponseEntity<Object> queryRepos(@Valid final PackageSearchCondition condition) {
        return coAdminService.queryAdminPackages(condition);
    }

    /**
     * get apply handle records by appid.
     *
     * @param applyIdString The handle form content id.
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/query/records")
    @RequestLimitRedis()
    @PreUserPermission(UerPermissionDef.COLLABORATION_PERMISSION_ADMIN)
    public ResponseEntity<Object> getApply(@RequestParam(value = "applyIdString") String applyIdString) {
        Long applyId = Long.valueOf(applyIdString);
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
        processApply.setApplyId(Long.valueOf(processApply.getApplyIdString()));
        return applyFormService.processApply(processApply);
    }


    /**
     * Query apply form based on the provided search condition by.
     *
     * @param condition The search condition for querying apply form.
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/query/apply")
    @RequestLimitRedis()
    @PreUserPermission(UerPermissionDef.COLLABORATION_PERMISSION_ADMIN)
    public ResponseEntity<Object> getApplyFrom(@Valid final ApplyFormSearchAdminCondition condition) {
        if (condition.getApplyIdString() != null) {
            condition.setApplyId(Long.valueOf(condition.getApplyIdString()));
        }
        return applyFormService.searchApplyFromByAdmin(condition);
    }

    /**
     * query repos applied to modify.
     *
     * @param applyStatus condition for querying apply repos.
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/apply/repos")
    @RequestLimitRedis()
    @PreUserPermission(UerPermissionDef.COLLABORATION_PERMISSION_ADMIN)
    public ResponseEntity<Object> queryApplyRepos(@RequestParam(value = "apply_status") String applyStatus) {
        return applyFormService.queryApplyReposByAdmin(applyStatus);
    }

    /**
     * query repos and sigs based on condition.
     *
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/query/repos")
    @RequestLimitRedis()
    public ResponseEntity<Object> queryRepoSigs() {
        return coAdminService.queryRepoSigs();
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
    @PreUserPermission(UerPermissionDef.COLLABORATION_PERMISSION_ADMIN)
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
    @PreUserPermission(UerPermissionDef.COLLABORATION_PERMISSION_ADMIN)
    public ResponseEntity<Object> revokeMyApply(@Valid @RequestBody MyApply myApply) {
        myApply.setApplyId(Long.valueOf(myApply.getApplyIdString()));
        return applyFormService.revokeMyApply(myApply);
    }
}
