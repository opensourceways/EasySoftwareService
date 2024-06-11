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

package com.easysoftware.infrastructure.eulerlifecycle.gatewayimpl;

import java.util.List;

import com.easysoftware.application.filedapplication.vo.EulerLifeCycleVo;
import com.easysoftware.domain.eulerlifecycle.gateway.EulerLifeCycleGateway;
import com.easysoftware.infrastructure.eulerlifecycle.gatewayimpl.coverter.EulerLifeCycleConverter;
import com.easysoftware.infrastructure.eulerlifecycle.gatewayimpl.dataobject.EulerLifeCycleDO;
import com.easysoftware.infrastructure.mapper.EulerLifeCycleDOMapper;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EulerLifeCycleGatewayImpl implements EulerLifeCycleGateway {

    /**
     * Autowired EulerLifeCycleDOMapper for database operations.
     */
    @Autowired
    private EulerLifeCycleDOMapper mapper;

    /**
     * Select all euler lifecycle and return them as a list of
     * EulerLifeCycleVo objects.
     *
     * @return A list of EulerLifeCycleVo objects containing all euler
     *         lifecycle
     */
    public List<EulerLifeCycleVo> selectAll() {
        List<EulerLifeCycleDO> doList = mapper.selectList(null);
        return EulerLifeCycleConverter.toVo(doList);
    }
}
