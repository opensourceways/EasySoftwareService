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

package com.easysoftware.application.rpmpackage;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easysoftware.application.rpmpackage.dto.RPMPackageNameSearchCondition;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDomainVo;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.rpmpackage.RPMPackageUnique;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;
import com.easysoftware.infrastructure.mapper.RPMPackageDOMapper;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Primary
@Service("RPMPackageService")
public class RPMPackageServiceImpl extends ServiceImpl<RPMPackageDOMapper, RPMPackageDO> implements RPMPackageService {
    /**
     * Resource for RPM Package Gateway.
     */
    @Resource
    private RPMPackageGateway rPMPkgGateway;
    /**
     * Value for Repository Maintainer API.
     */
    @Value("${api.repoMaintainer}")
    private String repoMaintainerApi;

    /**
     * Value for Repository Signature API.
     */
    @Value("${api.repoSig}")
    private String repoSigApi;

    /**
     * Value for Repository Download API.
     */
    @Value("${api.repoDownload}")
    private String repoDownloadApi;

    /**
     * Value for Kafka producer topic related to application version.
     */
    @Value("${producer.topic}")
    private String topicAppVersion;

    /**
     * Queries all RPM package menus.
     *
     * @param condition The search condition.
     * @return Map containing the RPM package menu.
     */
    @Override
    public Map<String, Object> queryAllRPMPkgMenu(final RPMPackageSearchCondition condition) {
        return rPMPkgGateway.queryMenuByName(condition);
    }

    /**
     * Queries all available openEuler version of RPM package.
     *
     * @param condition The search condition.
     * @return Map containing the RPM package menu.
     */
    @Override
    public ResponseEntity<Object> queryEulerVersionsByName(RPMPackageNameSearchCondition condition) {
        Map<String, Object> res = rPMPkgGateway.queryEulerVersionByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Queries all available openEuler archs of RPM package.
     *
     * @param condition The search condition.
     * @return Map containing the RPM package menu.
     */
    @Override
    public ResponseEntity<Object> queryEulerArchsByName(RPMPackageNameSearchCondition condition) {
        Map<String, Object> res = rPMPkgGateway.queryEulerArchsByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Searches for RPM packages.
     *
     * @param condition The search condition.
     * @return ResponseEntity with the search results.
     */
    @Override
    public ResponseEntity<Object> searchRPMPkg(final RPMPackageSearchCondition condition) {
        String pkgId = assemblePkgId(condition);

        List<RPMPackageDetailVo> rpmList = rPMPkgGateway.queryDetailByPkgId(pkgId);
        if (!rpmList.isEmpty()) {
            Map<String, Object> res = Map.ofEntries(
                    Map.entry("total", rpmList.size()),
                    Map.entry("list", rpmList));
            return ResultUtil.success(HttpStatus.OK, res);
        }

        Map<String, Object> res = rPMPkgGateway.queryDetailByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Assembles the package ID based on the RPM Package search condition.
     *
     * @param condition The RPM Package search condition.
     * @return The assembled package ID as a String.
     */
    private String assemblePkgId(final RPMPackageSearchCondition condition) {
        String os = StringUtils.trimToEmpty(condition.getOs());
        String subPath = StringUtils.trimToEmpty(condition.getSubPath());
        String name = StringUtils.trimToEmpty(condition.getName());
        String version = StringUtils.trimToEmpty(condition.getVersion());
        String arch = StringUtils.trimToEmpty(condition.getArch());

        StringBuilder cSb = new StringBuilder();
        cSb.append(os);
        cSb.append(subPath);
        cSb.append(name);
        cSb.append(version);
        cSb.append(arch);
        String pkgId = cSb.toString();
        return pkgId;
    }

    /**
     * Checks if an application exists.
     *
     * @param unique The name of the application.
     * @return true if the application exists, false otherwise.
     */
    @Override
    public boolean existApp(final String unique) {
        RPMPackageUnique uniquePkg = ObjectMapperUtil.jsonToObject(unique, RPMPackageUnique.class);
        return rPMPkgGateway.existRPM(uniquePkg);
    }

    /**
     * Saves a batch of data objects.
     *
     * @param dataObject List of data objects to save.
     */
    @Override
    @Transactional
    public void saveDataObjectBatch(final ArrayList<String> dataObject) {
        return;
    }

    /**
     * Queries part of the application package menu.
     *
     * @param condition The search condition.
     * @return List of RPMPackageDomainVo objects representing the queried data.
     */
    @Override
    public List<RPMPackageDomainVo> queryPartAppPkgMenu(final RPMPackageSearchCondition condition) {
        Map<String, Object> rPMMenu = rPMPkgGateway.queryPartRPMPkgMenu(condition);
        return (List<RPMPackageDomainVo>) rPMMenu.get("list");
    }

}
