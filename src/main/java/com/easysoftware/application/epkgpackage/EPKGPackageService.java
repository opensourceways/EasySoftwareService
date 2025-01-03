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

package com.easysoftware.application.epkgpackage;

import com.easysoftware.application.BaseIService;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageNameSearchCondition;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.dataobject.EPKGPackageDO;
import org.springframework.http.ResponseEntity;

public interface EPKGPackageService extends BaseIService<EPKGPackageDO> {
    /**
     * Searches for EPKG packages based on search conditions.
     *
     * @param condition EPKGPackageSearchCondition object.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> searchEPKGPkg(EPKGPackageSearchCondition condition);

    /**
     * Queries available openEuler version of epkg package.
     *
     * @param condition The search condition.
     * @return Map containing the openEuler versions.
     */
    ResponseEntity<Object> queryEulerVersionsByName(EPKGPackageNameSearchCondition condition);
}
