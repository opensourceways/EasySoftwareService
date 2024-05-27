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
import com.easysoftware.application.rpmpackage.RPMPackageService;
import com.easysoftware.application.rpmpackage.dto.RPMPackageNameSearchCondition;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.common.aop.RequestLimitRedis;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rpmpkg")
public class RPMPackageQueryAdapter {

    /**
     * Autowired service for handling RPM package-related operations.
     */
    @Autowired
    private RPMPackageService rPMPkgService;

    /**
     * Endpoint to search for RPM packages based on the provided search condition.
     *
     * @param condition The search condition for querying RPM packages.
     * @return ResponseEntity<Object>.
     */
    @GetMapping()
    @RequestLimitRedis() // dos-global设置 2s内 单一ip调用超5次触发
    public ResponseEntity<Object> searchRPMPkg(@Valid final RPMPackageSearchCondition condition) {
        return rPMPkgService.searchRPMPkg(condition);
    }

    /**
     * Endpoint to query for all avalaible openEuler version of RPM packages based
     * on the provided search condition.
     *
     * @param condition The search condition for querying RPM packages.
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/eulerver")
    @RequestLimitRedis()
    public ResponseEntity<Object> queryEulerVersionsByName(@Valid final RPMPackageNameSearchCondition condition) {
        return rPMPkgService.queryEulerVersionsByName(condition);
    }
}
