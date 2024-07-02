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
package com.easysoftware.application.oepackage;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.easysoftware.application.oepackage.dto.OEPackageSearchCondition;
import com.easysoftware.application.oepackage.dto.OepkgNameSearchCondition;

public interface OEPackageService {
    /**
     * Searches for OEpackages.
     *
     * @param condition The search condition.
     * @return ResponseEntity with the search results.
     */
    ResponseEntity<Object> searchOEPkg(OEPackageSearchCondition condition);

    /**
     * Queries all OEpackages menus.
     *
     * @param condition The search condition.
     * @return Map containing the OEpackages menu.
     */
    Map<String, Object> queryAllOEPkgMenu(OEPackageSearchCondition condition);

    /**
     * Queries available openEuler version of oepkg package.
     *
     * @param condition The search condition.
     * @return Map containing the openEuler versions.
     */
    ResponseEntity<Object> queryEulerVersionsByName(OepkgNameSearchCondition condition);
}
