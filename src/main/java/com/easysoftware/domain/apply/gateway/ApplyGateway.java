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

package com.easysoftware.domain.apply.gateway;

import com.easysoftware.application.apply.dto.ApplyHandleConditon;
import com.easysoftware.domain.apply.ApplyHandleRecord;

import java.util.List;

public interface ApplyGateway {
    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying apply handle records.
     * @return A map containing relevant information
     */
    List<ApplyHandleRecord> queryApplyhandleRecords(ApplyHandleConditon condition);

    /**
     * save apply handle record.
     *
     * @param record apply handle record.
     * @return save result
     */
    boolean savehandleRecord(ApplyHandleRecord record);
}
