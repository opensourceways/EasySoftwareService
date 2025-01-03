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

package com.easysoftware.application.applyform;

import com.easysoftware.application.applyform.dto.ApplyFormSearchAdminCondition;
import org.springframework.http.ResponseEntity;

import com.easysoftware.application.applyform.dto.ApplyFormSearchMaintainerCondition;
import com.easysoftware.application.applyform.dto.MyApply;
import com.easysoftware.application.applyform.dto.ProcessApply;

public interface ApplyFormService {

    /**
     * Search for apply form based on the provided search condition.
     *
     * @param condition The search condition for querying apply form.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> searchApplyFromByMaintainer(ApplyFormSearchMaintainerCondition condition);

    /**
     * process apply by applyid.
     *
     * @param processApply process apply.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> processApply(ProcessApply processApply);

    /**
     * Query apply form based on the provided search condition by.
     *
     * @param condition The search condition for querying apply form.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> searchApplyFromByAdmin(ApplyFormSearchAdminCondition condition);

    /**
     * Submit my apply.
     *
     * @param myApply The condition for submiting apply form.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> submitMyApplyWithLimit(MyApply myApply);

    /**
     * Revoke my apply.
     *
     * @param myApply The condition for revoking apply form.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> revokeMyApplyWithLimit(MyApply myApply);

    /**
     * Revoke my apply.
     *
     * @param myApply The condition for revoking apply form.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> revokeMyApply(MyApply myApply);

    /**
     * Update my apply.
     *
     * @param myApply The condition for update apply form.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> updateMyApplyWithLimit(MyApply myApply);

    /**
     * query repos by maintainer.
     *
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> queryApplyReposByMaintainer();

    /**
     * query repos by admin.
     *
     * @param applyStatus condition for querying apply repos.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> queryApplyReposByAdmin(String applyStatus);
}
