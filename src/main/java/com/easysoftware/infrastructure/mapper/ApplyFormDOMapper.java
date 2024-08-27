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
package com.easysoftware.infrastructure.mapper;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easysoftware.infrastructure.applyform.gatewayimpl.dataobject.ApplyFormDO;

public interface ApplyFormDOMapper extends BaseMapper<ApplyFormDO> {
    /**
     * process apply if apply status is OPEN.
     *
     * @param tableName apply form table.
     * @param status apply status.
     * @param applyFormDO apply content.
     * @return process result
     */
    int updateIfApplyOpen(@Param("tableName") String tableName, @Param("status") String status,
            @Param("applyForm") ApplyFormDO applyFormDO);
}
