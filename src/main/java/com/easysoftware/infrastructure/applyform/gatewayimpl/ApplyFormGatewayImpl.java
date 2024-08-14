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
package com.easysoftware.infrastructure.applyform.gatewayimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.applyform.dto.ApplyFormSearchMaintainerCondition;
import com.easysoftware.application.applyform.vo.ApplyFormContentVO;
import com.easysoftware.application.applyform.vo.ApplyFormSearchMaintainerVO;
import com.easysoftware.common.account.UserPermission;
import com.easysoftware.domain.applyform.gateway.ApplyFormGateway;
import com.easysoftware.infrastructure.apply.gatewayimpl.dataobject.ApplyhandleRecordsDO;
import com.easysoftware.infrastructure.applyform.gatewayimpl.converter.ApplyFormConvertor;
import com.easysoftware.infrastructure.applyform.gatewayimpl.dataobject.ApplyFormDO;
import com.easysoftware.infrastructure.mapper.ApplyFormDOMapper;
import com.easysoftware.infrastructure.mapper.ApplyHandleRecordsDOMapper;

@Component
public class ApplyFormGatewayImpl implements ApplyFormGateway {

    /**
     * Autowired field for the ApplicationVersionDOMapper.
     */
    @Autowired
    private ApplyFormDOMapper applyFormDOMapper;

    /**
     * Autowired field for the ApplyHandleRecordsDOMapper.
     */
    @Autowired
    private ApplyHandleRecordsDOMapper applyHandleRecordsDOMapper;

    /**
     * Autowired permission for the UserPermission.
     */
    @Autowired
    private UserPermission userPermission;

    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying apply form
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryApplyFormByPage(ApplyFormSearchMaintainerCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        String maintainer = userPermission.getUserLogin();

        Page<ApplyFormDO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ApplyFormDO> wrapper = new QueryWrapper<>();
        wrapper.eq("maintainer", maintainer);
        IPage<ApplyFormDO> resPage = applyFormDOMapper.selectPage(page, wrapper);

        long total = resPage.getTotal();
        List<ApplyFormDO> applyFormDOs = resPage.getRecords();
        List<ApplyFormSearchMaintainerVO> applyFormVOs = ApplyFormConvertor.toApplyFormVO(applyFormDOs);

        Map<String, Object> res = new HashMap<>();
        res.put("total", total);
        res.put("list", applyFormVOs);

        return res;

    }

    /**
     * Query information based on the provided search condition.
     *
     * @param applyId The search condition for querying apply form
     * @return A map containing relevant information
     */
    public Map<String, Object> queryApplyFormByApplyId(Long applyId) {
        String maintainer = userPermission.getUserLogin();
        QueryWrapper<ApplyFormDO> queryWrapperForm = new QueryWrapper<>();
        queryWrapperForm.eq("apply_id", applyId);
        queryWrapperForm.eq("maintainer", maintainer);

        List<ApplyFormDO> applyFormListDOs = applyFormDOMapper.selectList(queryWrapperForm);
        List<ApplyFormSearchMaintainerVO> applyFormListVOs = ApplyFormConvertor.toApplyFormVO(applyFormListDOs);

        QueryWrapper<ApplyhandleRecordsDO> queryWrapperRecords = new QueryWrapper<>();
        queryWrapperRecords.eq("apply_id", applyId);
        queryWrapperRecords.eq("maintainer", maintainer);

        List<ApplyhandleRecordsDO> applyhandleRecordsDOs = applyHandleRecordsDOMapper.selectList(queryWrapperRecords);

        List<ApplyFormContentVO> applyFormContentVOs = new ArrayList<>();
        for (ApplyFormSearchMaintainerVO applyFormVO : applyFormListVOs) {
            ApplyFormContentVO applyFormContentVO = new ApplyFormContentVO();
            BeanUtils.copyProperties(applyFormVO, applyFormContentVO);
            applyFormContentVO.setComment(applyhandleRecordsDOs.get(0).getComment());
            applyFormContentVOs.add(applyFormContentVO);
        }

        Map<String, Object> res = new HashMap<>();
        res.put("total", (long) applyFormContentVOs.size());
        res.put("list", applyFormContentVOs);

        return res;
    }

}
