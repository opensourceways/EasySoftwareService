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

package com.easysoftware.domain.oepackage.gateway;

import java.util.List;
import java.util.Map;

import com.easysoftware.application.oepackage.dto.OEPackageSearchCondition;
import com.easysoftware.application.oepackage.dto.OepkgNameSearchCondition;
import com.easysoftware.application.oepackage.vo.OEPackageDetailVo;

public interface OEPackageGateway {
    /**
     * Query detailed information based on the provided search condition for
     * OEpackages.
     *
     * @param condition The search condition for querying OEpackage details
     * @return A map containing detailed information
     */
    Map<String, Object> queryDetailByName(OEPackageSearchCondition condition);

    /**
     * Query detailed information by package ID for OEpackages.
     *
     * @param pkgId The package ID to query detailed information
     * @return A list of RPMPackageDetailVo objects
     */
    List<OEPackageDetailVo> queryDetailByPkgId(String pkgId);

    /**
     * Query menu items based on the provided search condition for OEpackages.
     *
     * @param condition The search condition for querying menu items
     * @return A map containing menu items
     */
    Map<String, Object> queryMenuByName(OEPackageSearchCondition condition);

    /**
     * Query columns based on the provided list of columns for OEpackages.
     *
     * @param columns The list of columns to query
     * @return A map containing column data
     */
    Map<String, List<String>> queryColumn(List<String> columns);

    /**
     * Get the total number of records in the RPM package table.
     *
     * @return The total number of records in the table
     */
    long queryTableLength();

    /**
     * Query Euler Version based on the provided search condition.
     *
     * @param condition The search condition for querying EulerVersion
     * @return A map containing tags information
     */
    Map<String, Object> queryEulerVersionByName(OepkgNameSearchCondition condition);

    /**
    * query pkg num of arch by os.
    * @param os os.
    * @return pkg nums of arch.
    */
   Map<String, Object> queryArchNum(String os);
}
