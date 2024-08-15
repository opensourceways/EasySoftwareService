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
package com.easysoftware.infrastructure.apply.gatewayimpl.converter;

import com.easysoftware.domain.apply.ApplyHandleRecord;
import com.easysoftware.infrastructure.apply.gatewayimpl.dataobject.ApplyhandleRecordsDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public final class ApplyHandleConverter {
    // Private constructor to prevent instantiation of the utility class
    private ApplyHandleConverter() {
        // private constructor to hide the implicit public one
        throw new AssertionError("Cannot instantiate ApplyHandleConverter class");
    }

    /**
     * Logger instance for ApplyHandleConverter.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplyHandleConverter.class);

    /**
     * Convert an ApplyhandleRecordsDO List to an ApplyHandleRecord List.
     *
     * @param doList The ApplyhandleRecordsDO List  to convert
     * @return An ApplyHandleRecord List
     */
    public static List<ApplyHandleRecord> toEntity(final List<ApplyhandleRecordsDO> doList) {
        List<ApplyHandleRecord> res = new ArrayList<>();
        for (ApplyhandleRecordsDO arecordDo : doList) {
            ApplyHandleRecord app = toEntity(arecordDo);
            res.add(app);
        }
        return res;
    }

    /**
     * Convert an ApplyhandleRecordsDO object to an ApplyHandleRecord entity.
     *
     * @param arecordDo The ApplyhandleRecordsDO object to convert
     * @return An ApplyHandleRecord entity
     */
    public static ApplyHandleRecord toEntity(final ApplyhandleRecordsDO arecordDo) {
        ApplyHandleRecord applyHandleRecord = new ApplyHandleRecord();
        BeanUtils.copyProperties(arecordDo, applyHandleRecord);
        return applyHandleRecord;
    }

    /**
     * Convert an ApplyHandleRecord entity to an ApplyhandleRecordsDO data object
     * specifically for creation.
     *
     * @param record The ApplyHandleRecord entity to convert
     * @return An ApplyhandleRecordsDO data object tailored for creation
     */
    public static ApplyhandleRecordsDO toDataObjectForCreate(final ApplyHandleRecord record) {
        ApplyhandleRecordsDO recordDO = new ApplyhandleRecordsDO();
        BeanUtils.copyProperties(record, recordDO);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        recordDO.setCreatedAt(currentTime);

        return recordDO;
    }
}
