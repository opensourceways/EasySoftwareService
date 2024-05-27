/* Copyright (c) 2024 openEuler Community
 EasySoftwareService is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

package com.easysoftware.adapter.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easysoftware.application.applicationversion.ApplicationVersionService;
import com.easysoftware.application.applicationversion.dto.ApplicationColumnSearchCondition;
import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
import com.easysoftware.common.aop.RequestLimitRedis;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appVersion")
public class ApplicationVersionQueryAdapter {
    /**
     * Autowired service for handling application version-related operations.
     */
    @Autowired
    private ApplicationVersionService appVersionService;

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
        return appVersionService.searchAppVerColumn(condition);
    }
}
