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

package com.easysoftware.infrastructure.collaboration.gatewayimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.easysoftware.application.collaboration.dto.PackageSearchCondition;
import com.easysoftware.application.collaboration.vo.PackageStatusVO;
import com.easysoftware.common.account.UserPermission;
import com.easysoftware.common.constant.PackageConstant;
import com.easysoftware.common.utils.EsAsyncHttpUtil;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.domain.collaboration.gateway.PackageStatusGateway;
import com.easysoftware.infrastructure.collaboration.gatewayimpl.converter.PackageStatusConverter;
import com.fasterxml.jackson.databind.JsonNode;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class PackageStatusGatewayImpl implements PackageStatusGateway {
    /**
     * Autowired field for the EsAsyncHttpUtil.
     */
    @Autowired
    private EsAsyncHttpUtil esAsyncHttpUtil;

    /**
     * Autowired UserPermission for check user permission.
     */
    @Autowired
    private UserPermission userPermission;

    /**
     * Logger instance for PackageStatusGatewayImpl.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PackageStatusGatewayImpl.class);

    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying package
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryByCondition(final PackageSearchCondition condition) {
        List<PackageStatusVO> pkgs = new ArrayList<>();
        int total = 0;
        try {
            Map<String, Object> query = ObjectMapperUtil.jsonToMap(condition);
            ListenableFuture<Response> future = esAsyncHttpUtil.executeSearch(PackageConstant.PACKAGE_STATUS_INDEX,
                    query, userPermission.getUserLogin());
            String responseBody = future.get().getResponseBody(UTF_8);
            JsonNode dataNode = ObjectMapperUtil.toJsonNode(responseBody);
            JsonNode hits = dataNode.path("hits").path("hits");
            total = dataNode.path("hits").path("total").path("value").asInt();
            pkgs = PackageStatusConverter.toDetail(hits);
        } catch (Exception e) {
            LOGGER.error("search package error - {}", e.getMessage());
        }
        return Map.ofEntries(Map.entry("total", total), Map.entry("list", pkgs));
    }
}
