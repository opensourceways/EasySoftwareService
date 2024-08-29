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
package com.easysoftware.domain.applyform.gateway;

import java.util.Map;

import com.easysoftware.application.applyform.dto.ApplyFormSearchAdminCondition;
import org.springframework.stereotype.Component;

import com.easysoftware.application.applyform.dto.ApplyFormSearchMaintainerCondition;
import com.easysoftware.application.applyform.dto.MyApply;
import com.easysoftware.application.applyform.dto.ProcessApply;


@Component
public interface ApplyFormGateway {

    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying apply form
     * @return A map containing relevant information
     */
    Map<String, Object> queryApplyFormByPage(ApplyFormSearchMaintainerCondition condition);

    /**
     * Query information based on the provided search condition by appId.
     *
     * @param applyId The search condition for querying apply form
     * @return A map containing relevant information
     */
    Map<String, Object> queryApplyFormByApplyId(Long applyId);

    /**
     * Process apply based on the provided condition..
     *
     * @param processApply The process result for apply.
     * @return A map containing relevant information
     */
    boolean processApply(ProcessApply processApply);

    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying apply form
     * @return A map containing relevant information
     */
    Map<String, Object> queryApplyFormByConditionAdmin(ApplyFormSearchAdminCondition condition);

    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying apply form
     * @return A map containing relevant information
     */
    Map<String, Object> queryApplyFormContentByConditionAdmin(ApplyFormSearchAdminCondition condition);

    /**
     * MyApply apply based on the provided condition..
     *
     * @param myApply The submit process result for apply.
     * @return A boolean
     */
    boolean submitMyApplyWithLimit(MyApply myApply);

    /**
     * MyApply apply based on the provided condition.
     *
     * @param myApply The revoke process result for apply.
     * @return A boolean
     */
    boolean revokeMyApplyWithLimit(MyApply myApply);

    /**
     * MyApply apply based on the provided condition.
     *
     * @param myApply The revoke process result for apply.
     * @return A boolean
     */
    boolean revokeMyApply(MyApply myApply);

    /**
     * MyApply apply based on the provided condition.
     *
     * @param myApply The update process result for apply.
     * @return A boolean
     */
    boolean updateMyApplyWithLimit(MyApply myApply);

    /**
     * MyApply apply based on the provided condition.
     *
     * @param applyId check the maintainer's limits of authority.
     * @param maintainer check the maintainer's limits of authority.
     * @return A boolean .
     */
    boolean checkMaintainerLimit(Long applyId, String maintainer);

    /**
     * Query repos by maintainer.
     *
     * @return A map containing relevant information
     */
    Map<String, Object> queryApplyReposByMaintainer();

    /**
     * query repos by admin.
     *
     * @return A map containing relevant information
     */
    Map<String, Object> queryApplyReposByAdmin();
}
