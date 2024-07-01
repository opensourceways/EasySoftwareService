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

package com.easysoftware.domain.epkgpackage.gateway;

import com.easysoftware.application.epkgpackage.dto.EPKGPackageNameSearchCondition;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageDetailVo;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageMenuVo;
import java.util.List;
import java.util.Map;

public interface EPKGPackageGateway {
    /**
     * Query detailed information based on the provided search condition.
     *
     * @param condition The search condition for querying EPKG package details
     * @return A map containing detailed information
     */
    Map<String, Object> queryDetailByName(EPKGPackageSearchCondition condition);

    /**
     * Query detailed information by package ID.
     *
     * @param pkgId The package ID to query detailed information
     * @return A list of EPKGPackageDetailVo objects
     */
    List<EPKGPackageDetailVo> queryDetailByPkgId(String pkgId);

    /**
     * Query menu items based on the provided search condition.
     *
     * @param condition The search condition for querying menu items
     * @return A map containing menu items
     */
    Map<String, Object> queryMenuByName(EPKGPackageSearchCondition condition);

    /**
     * Query columns based on the provided list of columns.
     *
     * @param columns The list of columns to query
     * @return A map containing column data
     */
    Map<String, List<String>> queryColumn(List<String> columns);

    /**
     * Get the total number of records in the table.
     *
     * @return The total number of records in the table
     */
    long queryTableLength();

    /**
     * Select a single EPKGPackageMenuVo object by name.
     *
     * @param name The name used to select the object
     * @return The selected EPKGPackageMenuVo object
     */
    EPKGPackageMenuVo selectOne(String name);

    /**
     * Query Euler Version based on the provided search condition.
     *
     * @param condition The search condition for querying EulerVersion
     * @return A map containing tags information
     */
    Map<String, Object> queryEulerVersionByName(EPKGPackageNameSearchCondition condition);

    /**
     * query pkg num of arch by os.
     * @param os os.
     * @return pkg nums of arch.
     */
    Map<String, Object> queryArchNum(String os);
}
