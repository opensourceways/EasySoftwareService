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

package com.easysoftware.domain.rpmpackage.gateway;

import com.easysoftware.application.rpmpackage.dto.RPMPackageNameSearchCondition;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.dto.RPMVersionCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageMenuVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageNewestVersionVo;
import com.easysoftware.application.rpmpackage.vo.PackgeVersionVo;

import java.util.List;
import java.util.Map;

public interface RPMPackageGateway {

    /**
     * Query detailed information based on the provided search condition for RPM
     * packages.
     *
     * @param condition The search condition for querying RPM package details
     * @return A map containing detailed information
     */
    Map<String, Object> queryDetailByName(RPMPackageSearchCondition condition);

    /**
     * Query detailed information by package ID for RPM packages.
     *
     * @param pkgId The package ID to query detailed information
     * @return A list of RPMPackageDetailVo objects
     */
    List<RPMPackageDetailVo> queryDetailByPkgId(String pkgId);

    /**
     * Query menu items based on the provided search condition for RPM packages.
     *
     * @param condition The search condition for querying menu items
     * @return A map containing menu items
     */
    Map<String, Object> queryMenuByName(RPMPackageSearchCondition condition);

    /**
     * Query columns based on the provided list of columns for RPM packages.
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
     * Query part of the RPM package menu based on the provided search condition.
     *
     * @param condition The search condition for querying a part of the RPM package
     *                  menu
     * @return A map containing relevant information
     */
    Map<String, Object> queryPartRPMPkgMenu(RPMPackageSearchCondition condition);

    /**
     * Select a single RPMPackageMenuVo object by name.
     *
     * @param name The name used to select the object
     * @return The selected RPMPackageMenuVo object
     */
    RPMPackageMenuVo selectOne(String name);

    /**
     * Query Euler Version based on the provided search condition.
     *
     * @param condition The search condition for querying EulerVersion
     * @return A map containing tags information
     */
    Map<String, Object> queryEulerVersionByName(RPMPackageNameSearchCondition condition);
    /**
     * Query the RPM newest version based on the provided search condition.
     *
     * @param condition The search condition for querying RPM newest version
     * @return List of RPM newest version
     */
    List<RPMPackageNewestVersionVo> queryNewstRpmVersion(RPMPackageNameSearchCondition condition);
    /**
     * Query the RPM version based on the provided search condition by os.
     *
     * @param condition The search condition for querying RPM newest version
     * @return List of RPM newest version
     */
    Map<String, List<PackgeVersionVo>> queryRpmVersionByOs(RPMVersionCondition condition);
    /**
     * query pkg num of arch by os.
     * @param os os.
     * @return pkg nums of arch.
     */
    Map<String, Object> queryArchNum(String os);
}
