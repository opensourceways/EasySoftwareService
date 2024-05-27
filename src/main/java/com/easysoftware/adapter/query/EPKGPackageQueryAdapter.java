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

import com.easysoftware.application.epkgpackage.EPKGPackageService;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageNameSearchCondition;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.common.aop.RequestLimitRedis;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/epkgpkg")
public class EPKGPackageQueryAdapter {

    /**
     * Autowired service for managing EPKGPackage packages.
     */
    @Autowired
    private EPKGPackageService ePKGPackageService;

    /**
     * Search for EPKG packages based on the provided search condition.
     *
     * @param condition The search condition for querying EPKG packages.
     * @return ResponseEntity<Object>.
     */
    @GetMapping()
    @RequestLimitRedis()
    public ResponseEntity<Object> searchEPKGPkg(@Valid final EPKGPackageSearchCondition condition) {
        return ePKGPackageService.searchEPKGPkg(condition);
    }

    /**
     * Endpoint to query for all avalaible openEuler version of epkg packages based
     * on the provided search condition.
     *
     * @param condition The search condition for querying epkg packages.
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/eulerver")
    @RequestLimitRedis()
    public ResponseEntity<Object> queryEulerVersionsByName(@Valid final EPKGPackageNameSearchCondition condition) {
        return ePKGPackageService.queryEulerVersionsByName(condition);
    }
}
