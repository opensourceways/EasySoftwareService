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
package com.easysoftware.infrastructure.apply.gatewayimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easysoftware.application.apply.dto.ApplyHandleConditon;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.domain.apply.ApplyHandleRecord;
import com.easysoftware.domain.apply.gateway.ApplyGateway;
import com.easysoftware.infrastructure.apply.gatewayimpl.converter.ApplyHandleConverter;
import com.easysoftware.infrastructure.apply.gatewayimpl.dataobject.ApplyhandleRecordsDO;
import com.easysoftware.infrastructure.mapper.ApplyHandleRecordsDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ApplyGatewayImpl implements ApplyGateway {

    /**
     * Autowired field for the ApplyHandleRecordsDOMapper.
     */
    @Autowired
    private ApplyHandleRecordsDOMapper applyHandleRecordsDOMapper;

    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying apply handle records.
     * @return A map containing relevant information
     */
    @Override
    public List<ApplyHandleRecord> queryApplyhandleRecords(ApplyHandleConditon condition) {


        QueryWrapper<ApplyhandleRecordsDO> wrapper = QueryWrapperUtil.createQueryWrapper(new ApplyhandleRecordsDO(),
                condition, null);
        wrapper.orderByDesc("created_at");

        List<ApplyhandleRecordsDO> appDOs = applyHandleRecordsDOMapper.selectList(wrapper);

        List<ApplyHandleRecord> applyHandleRecords = ApplyHandleConverter.toEntity(appDOs);

        return applyHandleRecords;
    }

    /**
     * save apply handle record.
     *
     * @param record apply handle record.
     * @return save result
     */
    @Override
    public boolean savehandleRecord(ApplyHandleRecord record) {
        ApplyhandleRecordsDO recordDO = ApplyHandleConverter.toDataObjectForCreate(record);
        int mark = applyHandleRecordsDOMapper.insert(recordDO);
        return mark == 1;
    }
}
