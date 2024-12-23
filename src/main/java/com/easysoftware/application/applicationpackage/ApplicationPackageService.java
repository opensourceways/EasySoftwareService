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

package com.easysoftware.application.applicationpackage;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageNameSearchCondition;
import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ApplicationPackageService {
    /**
     * Search for application packages based on the provided search condition.
     *
     * @param condition The search condition for querying application packages.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> searchAppPkg(ApplicationPackageSearchCondition condition);

    /**
     * Query a list of application package menus based on the provided search
     * condition.
     *
     * @param condition The search condition for querying application package menus.
     * @return List of ApplicationPackageMenuVo representing the menu list.
     */
    List<ApplicationPackageMenuVo> queryPkgMenuList(ApplicationPackageSearchCondition condition);

    /**
     * Query application packages based on specified tags.
     *
     * @param condition The search condition containing tags for querying
     *                  application packages.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> queryPkgByTags(ApplicationPackageNameSearchCondition condition);

    /**
     * Queries available openEuler version of application package.
     *
     * @param condition The search condition.
     * @return Map containing the openEuler versions.
     */
    ResponseEntity<Object> queryEulerVersionsByName(ApplicationPackageNameSearchCondition condition);
}
