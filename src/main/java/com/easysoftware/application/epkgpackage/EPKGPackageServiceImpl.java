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

package com.easysoftware.application.epkgpackage;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageNameSearchCondition;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageDetailVo;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.epkgpackage.EPKGPackageUnique;
import com.easysoftware.domain.epkgpackage.gateway.EPKGPackageGateway;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.dataobject.EPKGPackageDO;
import com.easysoftware.infrastructure.mapper.EPKGPackageDOMapper;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("EPKGPackageService")
public class EPKGPackageServiceImpl extends
        ServiceImpl<EPKGPackageDOMapper, EPKGPackageDO> implements EPKGPackageService {
    /**
     * Gateway for EPKG package operations.
     */
    @Resource
    private EPKGPackageGateway ePKGPackageGateway;

    /**
     * Queries all EPKG package menus based on search conditions.
     *
     * @param condition EPKGPackageSearchCondition object.
     * @return Map containing the menu data.
     */
    @Override
    public Map<String, Object> queryAllEPKGPkgMenu(final EPKGPackageSearchCondition condition) {
        return ePKGPackageGateway.queryMenuByName(condition);
    }

    /**
     * Searches for EPKG packages based on search conditions.
     *
     * @param condition EPKGPackageSearchCondition object.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> searchEPKGPkg(final EPKGPackageSearchCondition condition) {
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

        List<EPKGPackageDetailVo> epkgList = ePKGPackageGateway.queryDetailByPkgId(pkgId);

        if (!epkgList.isEmpty()) {
            Map<String, Object> res = Map.ofEntries(
                    Map.entry("total", epkgList.size()),
                    Map.entry("list", epkgList));
            return ResultUtil.success(HttpStatus.OK, res);
        }

        Map<String, Object> res = ePKGPackageGateway.queryDetailByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Saves a batch of data objects.
     *
     * @param dataObject ArrayList of data objects to save.
     */
    @Override
    public void saveDataObjectBatch(final ArrayList<String> dataObject) {
        return;
    }

    /**
     * Checks if an application with a given name exists.
     *
     * @param unique unique of the application.
     * @return boolean indicating if the application exists.
     */
    @Override
    public boolean existApp(final String unique) {
        EPKGPackageUnique uniquePkg = ObjectMapperUtil.jsonToObject(unique, EPKGPackageUnique.class);
        return ePKGPackageGateway.existEPKG(uniquePkg);
    }

    /**
     * Queries all available openEuler version of epkg package.
     *
     * @param condition The search condition.
     * @return Map containing the epkg package menu.
     */
    @Override
    public ResponseEntity<Object> queryEulerVersionsByName(EPKGPackageNameSearchCondition condition) {
        Map<String, Object> res = ePKGPackageGateway.queryEulerVersionByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }
}
