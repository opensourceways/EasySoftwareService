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
package com.easysoftware.infrastructure.eulerlifecycle.gatewayimpl.coverter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.easysoftware.application.filedapplication.vo.EulerLifeCycleVo;
import com.easysoftware.infrastructure.eulerlifecycle.gatewayimpl.dataobject.EulerLifeCycleDO;

public final class EulerLifeCycleConverter {

    // Private constructor to prevent instantiation of the PackageConstant class
    private EulerLifeCycleConverter() {
        // private constructor to hide the implicit public one
        throw new AssertionError("EulerLifeCycleConverter class cannot be instantiated.");
    }

    /**
     * Converts an EulerLifeCycleDO object to an EulerLifeCycleVo view object.
     *
     * @param doList The EulerLifeCycleDO object to convert.
     * @return The converted EulerLifeCycleVo view object.
     */
    public static List<EulerLifeCycleVo> toVo(final List<EulerLifeCycleDO> doList) {
        List<EulerLifeCycleVo> res = new ArrayList<>();
        for (EulerLifeCycleDO elDo : doList) {
            EulerLifeCycleVo elVo = new EulerLifeCycleVo();
            BeanUtils.copyProperties(elDo, elVo);
            res.add(elVo);
        }
        return res;
    }
}
