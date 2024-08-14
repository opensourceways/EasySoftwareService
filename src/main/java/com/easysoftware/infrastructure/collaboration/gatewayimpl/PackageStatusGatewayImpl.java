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
import com.easysoftware.infrastructure.applyform.gatewayimpl.dataobject.ApplyFormDO;
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
            String login = userPermission.getUserLogin();
            ListenableFuture<Response> future = esAsyncHttpUtil.executeSearch(PackageConstant.PACKAGE_STATUS_INDEX,
                    query, login);
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

    /**
     * update package status based on the provided condition.
     *
     * @param applyFormDO apply content
     * @return A map containing relevant information
     */
    @Override
    public boolean updateByMetric(final ApplyFormDO applyFormDO) {
        boolean flag = false;
        try {
            PackageSearchCondition condition = new PackageSearchCondition();
            condition.setRepo(applyFormDO.getRepo());
            String jsonStr = ObjectMapperUtil.writeValueAsString(queryByCondition(condition).get("list"));
            List<PackageStatusVO> pkgs = ObjectMapperUtil.toObjectList(PackageStatusVO.class, jsonStr);
            if (pkgs.size() != 1) {
                LOGGER.error("Duplicate src repo or no repo");
                return flag;
            }
            String status = computeMetric(pkgs.get(0));
            ListenableFuture<Response> future = esAsyncHttpUtil.executeUpdate(PackageConstant.PACKAGE_STATUS_INDEX,
                    applyFormDO, status);

            String responseBody = future.get().getResponseBody(UTF_8);
            int total = ObjectMapperUtil.toJsonNode(responseBody).path("total").asInt();
            if (total == 0) {
                LOGGER.error("No update permission");
            }
            int code = future.get().getStatusCode();
            flag = code == 200 && total == 1;
        } catch (Exception e) {
            LOGGER.error("update package error - {}", e.getMessage());
        }
        return flag;
    }

    /**
     * compute package status.
     *
     * @param pkgStatus package status
     * @return A map containing relevant information
     */
    public String computeMetric(PackageStatusVO pkgStatus) {
        String status = "Other";
        if (PackageConstant.CVE_ALL_NO_FIXED.equals(pkgStatus.getCveStatus())
                && PackageConstant.ISSUE_ALL_NO_FIXED.equals(pkgStatus.getIssueStatus())) {
            status = PackageConstant.NO_MAINTAINENANCE;
        } else if (PackageConstant.CVE_ALL_NO_FIXED.equals(pkgStatus.getCveStatus())) {
            status = PackageConstant.LACK_OF_MAINTAINENANCE;
        } else if (PackageConstant.CVE_ALL_FIXED.equals(pkgStatus.getCveStatus())) {
            status = PackageConstant.HEALTH;
        } else if (PackageConstant.NO_CVE.equals(pkgStatus.getCveStatus())
                && PackageConstant.PR_NO_UPDATED.equals(pkgStatus.getPrStatus())
                && PackageConstant.LATEST_VERSION.equals(pkgStatus.getVersionStatus())) {
            status = PackageConstant.HEALTH;
        } else if (PackageConstant.NO_CVE.equals(pkgStatus.getCveStatus())
                && PackageConstant.PR_NO_UPDATED.equals(pkgStatus.getPrStatus())
                && PackageConstant.OUTDATED_VERSION.equals(pkgStatus.getVersionStatus())) {
            status = PackageConstant.INACTIVE;
        } else if (PackageConstant.NO_CVE.equals(pkgStatus.getCveStatus())) {
            status = PackageConstant.ACTIVE;
        }
        return status;
    }
}
