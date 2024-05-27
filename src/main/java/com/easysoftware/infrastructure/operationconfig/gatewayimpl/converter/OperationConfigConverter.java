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

package com.easysoftware.infrastructure.operationconfig.gatewayimpl.converter;

import com.easysoftware.application.operationconfig.vo.OperationConfigVo;
import com.easysoftware.infrastructure.operationconfig.gatewayimpl.dataobject.OperationConfigDO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public final class OperationConfigConverter {
    // Private constructor to prevent instantiation of the PackageConstant class
    private OperationConfigConverter() {
        // private constructor to hide the implicit public one
        throw new AssertionError("OperationConfigConverter class cannot be instantiated.");
    }

    /**
     * Converts a list of OperationConfigDO objects to a list of OperationConfigVo view objects.
     *
     * @param doList The list of OperationConfigDO objects to convert.
     * @return A list of OperationConfigVo view objects.
     */
    public static List<OperationConfigVo> toVo(final List<OperationConfigDO> doList) {
        List<OperationConfigVo> res = new ArrayList<>();
        for (OperationConfigDO op : doList) {
            OperationConfigVo vo = toVo(op);
            res.add(vo);
        }
        return res;
    }

    /**
     * Converts an OperationConfigDO object to an OperationConfigVo view object.
     *
     * @param opDo The OperationConfigDO object to convert.
     * @return The converted OperationConfigVo view object.
     */
    public static OperationConfigVo toVo(final OperationConfigDO opDo) {
        OperationConfigVo opVo = new OperationConfigVo();
        BeanUtils.copyProperties(opDo, opVo);
        return opVo;
    }


}
