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
package com.easysoftware.infrastructure.applyform.gatewayimpl.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.easysoftware.application.applyform.dto.ProcessApply;
import com.easysoftware.application.applyform.vo.ApplyFormSearchMaintainerVO;
import com.easysoftware.domain.applyform.ApplyForm;
import com.easysoftware.infrastructure.applyform.gatewayimpl.dataobject.ApplyFormDO;


public final class ApplyFormConvertor {

    // Private constructor to prevent instantiation of the utility class
    private ApplyFormConvertor() {
        // private constructor to hide the implicit public one
        throw new AssertionError("Cannot instantiate ApplyFormConvertor class");
    }

    /**
     * Convert an ApplyFormDO object to an ApplyForm entity.
     *
     * @param applyFormDO The ApplyFormDO object to convert
     * @return An ApplyForm entity
     */
    public static ApplyForm toEntity(final ApplyFormDO applyFormDO) {
        ApplyForm applyForm = new ApplyForm();
        BeanUtils.copyProperties(applyFormDO, applyForm);
        return applyForm;
    }

    /**
     * Convert an ProcessApply object to an ApplyFormDO entity.
     *
     * @param processApply The ProcessApply object to convert
     * @return An ApplyForm entity
     */
    public static ApplyFormDO toApplyFormDO(final ProcessApply processApply) {
        ApplyFormDO applyForm = new ApplyFormDO();
        BeanUtils.copyProperties(processApply, applyForm);
        return applyForm;
    }

    /**
     * Convert a list of ApplicationVersionDO objects to a list of
     * ApplyForm entities.
     *
     * @param applyFormDOs The list of ApplicationVersionDO objects to convert
     * @return A list of ApplyForm entities
     */
    public static List<ApplyForm> toEntity(final List<ApplyFormDO> applyFormDOs) {
        List<ApplyForm> res = new ArrayList<>();
        for (ApplyFormDO applyFormDO : applyFormDOs) {
            ApplyForm applyForm = toEntity(applyFormDO);
            res.add(applyForm);
        }
        return res;
    }

    /**
     * Convert a list of ApplicationVersionDO objects to a list of
     * ApplyFormVOs Lists.
     *
     * @param applyFormDO The ApplyFormSearchMaintainerVO objects to convert
     * @return A list of ApplyForm entities
     */
    public static ApplyFormSearchMaintainerVO toApplyFormVO(final ApplyFormDO applyFormDO) {
        ApplyFormSearchMaintainerVO applyFormVO = new ApplyFormSearchMaintainerVO();
        BeanUtils.copyProperties(applyFormDO, applyFormVO);
        return applyFormVO;
    }

    /**
     * Convert a list of ApplicationVersionDO objects to a list of
     * ApplyFormVOs Lists.
     *
     * @param applyFormDOs The list of ApplyFormSearchMaintainerVO objects to convert
     * @return A list of ApplyForm entities
     */
    public static List<ApplyFormSearchMaintainerVO> toApplyFormVO(final List<ApplyFormDO> applyFormDOs) {
        List<ApplyFormSearchMaintainerVO> res = new ArrayList<>();
        for (ApplyFormDO applyFormDO : applyFormDOs) {
            ApplyFormSearchMaintainerVO applyFormVO = toApplyFormVO(applyFormDO);
            res.add(applyFormVO);
        }
        return res;
    }

}
