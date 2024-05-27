/* Copyright (c) 2024 openEuler Community
 EasySoftwareService is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

package com.easysoftware.infrastructure.operationconfig.gatewayimpl;

import com.easysoftware.application.operationconfig.vo.OperationConfigVo;
import com.easysoftware.domain.operationconfig.gateway.OperationConfigGateway;
import com.easysoftware.infrastructure.mapper.OperationConfigDOMapper;
import com.easysoftware.infrastructure.operationconfig.gatewayimpl.converter.OperationConfigConverter;
import com.easysoftware.infrastructure.operationconfig.gatewayimpl.dataobject.OperationConfigDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationConfigGatewayImpl implements OperationConfigGateway {
    /**
     * Autowired OperationConfigDOMapper for database operations.
     */
    @Autowired
    private OperationConfigDOMapper mapper;

    /**
     * Select all operation configurations and return them as a list of
     * OperationConfigVo objects.
     *
     * @return A list of OperationConfigVo objects containing all operation
     *         configurations
     */
    @Override
    public List<OperationConfigVo> selectAll() {
        List<OperationConfigDO> doList = mapper.selectList(null);
        return OperationConfigConverter.toVo(doList);
    }
}
