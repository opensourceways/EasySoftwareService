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

package com.easysoftware.domain.applicationpackage.gateway;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageNameSearchCondition;
import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageDetailVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;

import java.util.List;
import java.util.Map;

public interface ApplicationPackageGateway {

    /**
     * Query menu items based on the provided search condition.
     *
     * @param condition The search condition for querying menu items
     * @return A map containing menu items
     */
    Map<String, Object> queryMenuByName(ApplicationPackageSearchCondition condition);

    /**
     * Query detailed information based on the provided search condition.
     *
     * @param condition The search condition for querying detailed information
     * @return A map containing detailed information
     */
    Map<String, Object> queryDetailByName(ApplicationPackageSearchCondition condition);

    /**
     * Query detailed information by package ID.
     *
     * @param pkgId The package ID to query detailed information
     * @return A list of ApplicationPackageDetailVo objects
     */
    List<ApplicationPackageDetailVo> queryDetailByPkgId(String pkgId);

    /**
     * Get the total number of records in the table.
     *
     * @return The total number of records in the table
     */
    long queryTableLength();

    /**
     * Select a single ApplicationPackageMenuVo object by name.
     *
     * @param name The name used to select the object
     * @return The selected ApplicationPackageMenuVo object
     */
    ApplicationPackageMenuVo selectOne(String name);

    /**
     * Query columns based on the provided list of columns.
     *
     * @param columns The list of columns to query
     * @return A map containing column data
     */
    Map<String, List<String>> queryColumn(List<String> columns);

    /**
     * Query tags based on the provided search condition.
     *
     * @param condition The search condition for querying tags
     * @return A map containing tags information
     */
    Map<String, Object> queryTagsByName(ApplicationPackageNameSearchCondition condition);

    /**
     * Query Euler Version based on the provided search condition.
     *
     * @param condition The search condition for querying EulerVersion
     * @return A map containing tags information
     */
    Map<String, Object> queryEulerVersionByName(ApplicationPackageNameSearchCondition condition);
}
