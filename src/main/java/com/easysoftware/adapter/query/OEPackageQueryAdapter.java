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

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easysoftware.application.oepackage.dto.OEPackageSearchCondition;
import com.easysoftware.application.oepackage.OEPackageService;
import com.easysoftware.common.aop.RequestLimitRedis;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/oepkg")
public class OEPackageQueryAdapter {
    /**
     * Autowired service for handling OEpackage-related operations.
     */
    @Autowired
    private OEPackageService oEPackageService;

    /**
     * Endpoint to search for OEpackages based on the provided search condition.
     *
     * @param condition The search condition for querying OEpackages.
     * @return ResponseEntity<Object>.
     */
    @GetMapping()
    @RequestLimitRedis()
    public ResponseEntity<Object> searchOEpkg(@Valid final OEPackageSearchCondition condition) {
        return oEPackageService.searchOEPkg(condition);
    }
}
