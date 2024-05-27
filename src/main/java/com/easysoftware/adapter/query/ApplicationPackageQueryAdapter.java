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
import com.easysoftware.application.applicationpackage.ApplicationPackageService;
import com.easysoftware.application.applicationpackage.dto.ApplicationPackageNameSearchCondition;
import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.common.aop.RequestLimitRedis;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/apppkg")
public class ApplicationPackageQueryAdapter {

    /**
     * Autowired service for managing application packages.
     */
    @Autowired
    private ApplicationPackageService appPkgService;

    /**
     * Endpoint to query application packages by name.
     *
     * @param condition The search condition for querying application packages.
     * @return ResponseEntity<Object>.
     */
    @GetMapping("")
    @RequestLimitRedis(period = 10, count = 5) // 10s内同一ip连续访问5次，拒绝访问
    public ResponseEntity<Object> queryByName(@Valid final ApplicationPackageSearchCondition condition) {
        return appPkgService.searchAppPkg(condition);
    }

    /**
     * Endpoint to query application packages by tags.
     *
     * @param condition The search condition for querying application packages.
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/tags")
    @RequestLimitRedis()
    public ResponseEntity<Object> queryByTags(@Valid final ApplicationPackageNameSearchCondition condition) {
        return appPkgService.queryPkgByTags(condition);
    }

    /**
     * Endpoint to query for all avalaible openEuler version of application packages
     * based
     * on the provided search condition.
     *
     * @param condition The search condition for querying application packages.
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/eulerver")
    @RequestLimitRedis()
    public ResponseEntity<Object> queryEulerVersionsByName(
            @Valid final ApplicationPackageNameSearchCondition condition) {
        return appPkgService.queryEulerVersionsByName(condition);
    }
}
