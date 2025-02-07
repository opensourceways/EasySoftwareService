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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.easysoftware.application.collaboration.dto.PackageSearchCondition;
import com.easysoftware.application.collaboration.vo.PackageDetailUrlVo;
import com.easysoftware.application.collaboration.vo.PackageStatusVo;
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
     * Value injected for the es package status index name.
     */
    @Value("${es.pkgIndex}")
    private String pkgIndex;

    /**
     * Value injected for the es package status index name.
     */
    @Value("${status_detail_url.repo_detail_url}")
    private String repoDetailUrl;

    /**
     * Value injected for the es package status index name.
     */
    @Value("${status_detail_url.unfixed_cve_url}")
    private String unfixedCveUrl;

    /**
     * Value injected for the es package status index name.
     */
    @Value("${status_detail_url.version_url}")
    private String versionUrl;

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
        String login = userPermission.getUserLogin();
        return queryRepoByCondition(condition, login);
    }

    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying package
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryAllByCondition(final PackageSearchCondition condition) {
        return queryRepoByCondition(condition, "*");
    }

    /**
     * Query repos based on the provided search condition.
     *
     * @param condition The search condition for querying package
     * @param login user gitee id
     * @return A map containing relevant information
     */
    public Map<String, Object> queryRepoByCondition(final PackageSearchCondition condition, String login) {
        List<PackageStatusVo> pkgs = new ArrayList<>();
        int total = 0;
        try {
            Map<String, Object> query = ObjectMapperUtil.jsonToMap(condition);
            ListenableFuture<Response> future = esAsyncHttpUtil.executeSearch(pkgIndex, query, login);
            String responseBody = future.get().getResponseBody(UTF_8);
            JsonNode dataNode = ObjectMapperUtil.toJsonNode(responseBody);
            JsonNode hits = dataNode.path("hits").path("hits");
            total = dataNode.path("hits").path("total").path("value").asInt();
            PackageDetailUrlVo pkgDetailUrl = new PackageDetailUrlVo(unfixedCveUrl, repoDetailUrl, repoDetailUrl,
                    versionUrl);
            pkgs = PackageStatusConverter.toDetail(hits, pkgDetailUrl);
        } catch (Exception e) {
            LOGGER.error("search package error - {}", e.getMessage());
        }
        return Map.ofEntries(Map.entry("total", total), Map.entry("list", pkgs));
    }

    /**
     * update package status based on the provided condition.
     *
     * @param applyFormDO apply content
     * @return The result of update
     */
    @Override
    public boolean updateByMetric(final ApplyFormDO applyFormDO) {
        boolean flag = false;
        try {
            PackageSearchCondition condition = new PackageSearchCondition();
            condition.setRepo(applyFormDO.getRepo());
            String jsonStr = ObjectMapperUtil.writeValueAsString(queryAllByCondition(condition).get("list"));
            List<PackageStatusVo> pkgs = ObjectMapperUtil.toObjectList(PackageStatusVo.class, jsonStr);
            if (pkgs.size() != 1) {
                LOGGER.error("Duplicate src repo or no repo");
                return flag;
            }
            PackageStatusVo pkg = pkgs.get(0);
            updatePackageStatus(pkg, applyFormDO);

            String status = computeMetric(pkg);
            ListenableFuture<Response> future = esAsyncHttpUtil.executeUpdate(pkgIndex, applyFormDO, status);

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
     * @param pkgStatus package metric status
     * @return package status
     */
    public String computeMetric(PackageStatusVo pkgStatus) {
        String status = PackageConstant.ACTIVE;
        if (PackageConstant.CVE_ALL_NO_FIXED.equals(pkgStatus.getCveStatus())
                && PackageConstant.ISSUE_ALL_NO_FIXED.equals(pkgStatus.getIssueStatus())) {
            status = PackageConstant.NO_MAINTAINENANCE;
        } else if (PackageConstant.CVE_ALL_NO_FIXED.equals(pkgStatus.getCveStatus())) {
            status = PackageConstant.LACK_OF_MAINTAINENANCE;
        } else if (PackageConstant.CVE_SOME_FIXED.equals(pkgStatus.getCveStatus())) {
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

    /**
     * compute package status.
     *
     * @param pkgStatus   package metric status
     * @param applyFormDO apply content
     * @return package status
     */
    public PackageStatusVo updatePackageStatus(PackageStatusVo pkgStatus, ApplyFormDO applyFormDO) {
        if (PackageConstant.PKG_CVE_METRIC.equalsIgnoreCase(applyFormDO.getMetric())) {
            pkgStatus.setCveStatus(applyFormDO.getMetricStatus());
        } else if (PackageConstant.PKG_VERSION_METRIC.equalsIgnoreCase(applyFormDO.getMetric())) {
            pkgStatus.setVersionStatus(applyFormDO.getMetricStatus());
        } else {
            LOGGER.error("update error - metric {} cannot be updated", applyFormDO.getMetric());
        }
        return pkgStatus;
    }

    /**
     * query repos and sigs based on condition.
     *
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryRepoSigsByAdmin() {
        Map<String, Object> res = queryRepoSigs(null);
        return res;
    }

    /**
     * query repos and sigs based on condition.
     *
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryRepoSigsByMaintainer() {
        String maintainer = userPermission.getUserLogin();
        Map<String, Object> res = queryRepoSigs(maintainer);
        return res;
    }

    /**
     * query repos and sigs based on condition.
     *
     * @param login user gitee id
     * @return A map containing relevant information
     */
    public Map<String, Object> queryRepoSigs(String login) {
        login = login == null ? "*" : login;
        Map<String, Object> res = new HashMap<>();
        try {
            ListenableFuture<Response> future = esAsyncHttpUtil.executeSearch(pkgIndex, login);
            String responseBody = future.get().getResponseBody(UTF_8);
            JsonNode dataNode = ObjectMapperUtil.toJsonNode(responseBody);
            JsonNode sigs = dataNode.path("aggregations").path("sigs").path("buckets");
            for (JsonNode sig : sigs) {
                JsonNode repos = sig.path("repos").path("buckets");
                for (JsonNode repo : repos) {
                    res.put(repo.path("key").asText(), sig.path("key"));
                }
            }
        } catch (Exception e) {
            LOGGER.error("search repo error");
        }
        return res;
    }
}
