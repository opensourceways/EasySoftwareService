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

package com.easysoftware.domain.collaboration.gateway;

import java.util.Map;

import com.easysoftware.application.collaboration.dto.PackageSearchCondition;
import com.easysoftware.infrastructure.applyform.gatewayimpl.dataobject.ApplyFormDO;

public interface PackageStatusGateway {
    /**
     * Query detailed information based on the provided search condition.
     *
     * @param condition The search condition for querying detailed information
     * @return A map containing detailed information
     */
    Map<String, Object> queryByCondition(PackageSearchCondition condition);

    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying package
     * @return A map containing relevant information
     */
    Map<String, Object> queryAllByCondition(PackageSearchCondition condition);

    /**
     * update package status based on the provided condition.
     *
     * @param applyFormDO apply content
     * @return A map containing relevant information
     */
    boolean updateByMetric(ApplyFormDO applyFormDO);

    /**
     * query repos and sigs based on condition.
     *
     * @return A map containing relevant information
     */
    Map<String, Object> queryRepoSigsByAdmin();

    /**
     * query repos and sigs based on condition.
     *
     * @return A map containing relevant information
     */
    Map<String, Object> queryRepoSigsByMaintainer();
}
