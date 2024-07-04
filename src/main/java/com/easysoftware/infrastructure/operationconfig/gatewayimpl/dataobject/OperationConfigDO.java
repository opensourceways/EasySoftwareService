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

package com.easysoftware.infrastructure.operationconfig.gatewayimpl.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.easysoftware.common.constant.PackageConstant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(PackageConstant.OPER_CONF_TABLE)
public class OperationConfigDO {
    /**
     * Serializable class with a defined serial version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Categories information.
     */
    private String categorys;

    /**
     * Order index information.
     */
    private String orderIndex;

    /**
     * Recommendation status.
     */
    private String recommend;

    /**
     * Type of the entity.
     */
    private String type;

}
