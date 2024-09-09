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

package com.easysoftware.domain.applicationversion.gateway;

import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
import com.easysoftware.application.rpmpackage.vo.PackgeVersionVo;

import java.util.List;
import java.util.Map;

public interface ApplicationVersionGateway {

    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying application versions
     * @return A map containing relevant information
     */
    Map<String, Object> queryByName(ApplicationVersionSearchCondition condition);

    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying application versions
     * @return A map containing relevant information
     */
    Map<String, Object> queryByEulerOsVersion(ApplicationVersionSearchCondition condition);

    /**
     * Search column.
     *
     * @param columns columns
     * @return A collection of ApplicationVersionDO objects
     */
    Map<String, List<String>> queryColumn(List<String> columns);

    /**
     * Query all upstream package version.
     *
     * @return A map containing relevant information
     */
    Map<String, List<PackgeVersionVo>> queryUpstreamVersions();
}
