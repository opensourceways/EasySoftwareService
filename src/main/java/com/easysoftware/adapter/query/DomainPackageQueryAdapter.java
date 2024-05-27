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
import com.easysoftware.application.domainpackage.DomainPackageService;
import com.easysoftware.application.domainpackage.dto.DomainColumnCondition;
import com.easysoftware.application.domainpackage.dto.DomainDetailSearchCondition;
import com.easysoftware.application.domainpackage.dto.DomainSearchCondition;
import com.easysoftware.common.aop.RequestLimitRedis;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/domain")
public class DomainPackageQueryAdapter {

    /**
     * Autowired service for managing domain packages.
     */
    @Autowired
    private DomainPackageService domainService;

    /**
     * Endpoint to query domains by name.
     *
     * @param condition The search condition for querying domains.
     * @return ResponseEntity<Object>.
     */
    @GetMapping()
    @RequestLimitRedis()
    public ResponseEntity<Object> queryByName(@Valid final DomainSearchCondition condition) {
        return domainService.searchDomain(condition);
    }

    /**
     * Endpoint to query detailed information about a domain.
     *
     * @param condition The search condition for querying domain details.
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/detail")
    @RequestLimitRedis()
    public ResponseEntity<Object> queryDomainDetail(@Valid final DomainDetailSearchCondition condition) {
        return domainService.searchDomainDetail(condition);
    }

    /**
     * Endpoint to query domain columns.
     *
     * @param condition The search condition for querying domain columns.
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/column")
    @RequestLimitRedis()
    public ResponseEntity<Object> queryColumn(@Valid final DomainColumnCondition condition) {
        return domainService.searchColumn(condition);
    }

    /**
     * Endpoint to query domain statistics.
     *
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/stat")
    @RequestLimitRedis()
    public ResponseEntity<Object> queryStat() {
        return domainService.queryStat();
    }


}
