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

package com.easysoftware.application.applicationversion;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easysoftware.application.applicationversion.dto.ApplicationColumnSearchCondition;
import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.applicationversion.gateway.ApplicationVersionGateway;
import com.easysoftware.infrastructure.applicationversion.gatewayimpl.dataobject.ApplicationVersionDO;
import com.easysoftware.infrastructure.mapper.ApplicationVersionDOMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Service("ApplicationVersionService")
public class ApplicationVersionServiceImpl extends ServiceImpl<ApplicationVersionDOMapper, ApplicationVersionDO>
        implements ApplicationVersionService {
    /**
     * API endpoint for repository information.
     */
    @Value("${api.repoInfo}")
    private String repoInfoApi;

    /**
     * Resource for interacting with Application Version Gateway.
     */
    @Resource
    private ApplicationVersionGateway appVersionGateway;

    /**
     * Searches for application versions based on the specified search conditions.
     *
     * @param condition The search conditions to filter application versions.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> searchAppVersion(final ApplicationVersionSearchCondition condition) {
        Map<String, Object> res = appVersionGateway.queryByEulerOsVersion(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Saves a batch of data objects.
     *
     * @param dataObject An ArrayList containing the data objects to be saved.
     */
    @Override
    public void saveDataObjectBatch(final ArrayList<String> dataObject) {
        return;
    }

    /**
     * Search column.
     *
     * @param condition condition.
     */
    @Override
    public ResponseEntity<Object> searchAppVerColumn(ApplicationColumnSearchCondition condition) {
        List<String> columns = QueryWrapperUtil.splitStr(condition.getColumn());
        Map<String, List<String>> res = appVersionGateway.queryColumn(columns);
        return ResultUtil.success(HttpStatus.OK, res);
    }
}
