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

package com.easysoftware.application.domainpackage;

import org.springframework.http.ResponseEntity;

import com.easysoftware.application.domainpackage.dto.DomainColumnCondition;
import com.easysoftware.application.domainpackage.dto.DomainDetailSearchCondition;
import com.easysoftware.application.domainpackage.dto.DomainSearchCondition;

public interface DomainPackageService {
    /**
     * Search for domains based on the provided search condition.
     *
     * @param condition The DomainSearchCondition for searching.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> searchDomain(DomainSearchCondition condition);

    /**
     * Search for domain details based on the provided search condition.
     *
     * @param condition The DomainDetailSearchCondition for searching.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> searchDomainDetail(DomainDetailSearchCondition condition);

    /**
     * Search for columns based on the provided condition.
     *
     * @param condition The DomainColumnCondition for searching.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> searchColumn(DomainColumnCondition condition);

    /**
     * Query statistics.
     *
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> queryStat();

}
