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

import com.easysoftware.application.collaboration.CoMaintainerService;
import com.easysoftware.application.collaboration.dto.PackageSearchCondition;
import com.easysoftware.common.account.UserPermission;
import com.easysoftware.common.annotation.CoMaintainerPermission;
import com.easysoftware.common.annotation.CoUserRepoPermission;
import com.easysoftware.common.aop.RequestLimitRedis;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ResultUtil;

import jakarta.validation.Valid;

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
     * Autowired UserPermission for check user permission.
     */
    @Autowired
    private UserPermission userPermission;

    /**
     * Endpoint to search for repos based on the provided search
     * condition.
     *
     * @param repo repo name.
     * @return ResponseEntity<Object>.
     */
    @GetMapping()
    @RequestLimitRedis()
    @CoUserRepoPermission()
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
    @GetMapping("/permission")
    @RequestLimitRedis()
    public ResponseEntity<Object> checkPermission() {
        HashMap<String, Boolean> result = new HashMap<>();
        try {
            HashSet<String> permissionRepos = userPermission.getUserRepoList();

            if (permissionRepos.size() > 0) {
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
}
