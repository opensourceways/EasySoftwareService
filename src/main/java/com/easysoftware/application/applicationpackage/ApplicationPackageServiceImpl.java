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

package com.easysoftware.application.applicationpackage;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageNameSearchCondition;
import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageDetailVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageTagsVo;
import com.easysoftware.common.exception.NoneResException;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ApplicationPackageServiceImpl implements ApplicationPackageService {

    /**
     * Resource injection for the ApplicationPackageGateway.
     */
    @Resource
    private ApplicationPackageGateway appPkgGateway;

    /**
     * Value injection for the repoMaintainerApi configuration property.
     */
    @Value("${api.repoMaintainer}")
    private String repoMaintainerApi;

    /**
     * Value injection for the repoInfoApi configuration property.
     */
    @Value("${api.repoInfo}")
    private String repoInfoApi;

    /**
     * Value injection for the repoDownloadApi configuration property.
     */
    @Value("${api.repoDownload}")
    private String repoDownloadApi;

    /**
     * Value injection for the repoSigApi configuration property.
     */
    @Value("${api.repoSig}")
    private String repoSigApi;

    /**
     * Query application packages based on specified tags.
     *
     * @param condition The search condition containing tags for querying
     *                  application packages.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> queryPkgByTags(final ApplicationPackageNameSearchCondition condition) {
        Map<String, Object> res = appPkgGateway.queryTagsByName(condition);
        Long total = (Long) res.get("total");
        List<ApplicationPackageTagsVo> list = (List<ApplicationPackageTagsVo>) res.get("list");
        if (total == 0 || list.size() == 0) {
            throw new ParamErrorException("the tag does not exist");
        }
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Search for application packages based on the provided search condition.
     *
     * @param condition The search condition for querying application packages.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> searchAppPkg(final ApplicationPackageSearchCondition condition) {
        condition.setTimeOrder("");
        if (StringUtils.isBlank(condition.getPkgId())) {
            throw new ParamErrorException("the pkgid can not be null");
        }

        Map<String, Object> res = appPkgGateway.queryDetailByName(condition);
        Long total = (Long) res.get("total");
        List<ApplicationPackageDetailVo> list = (List<ApplicationPackageDetailVo>) res.get("list");
        if (total == 0 || list.size() == 0) {
            throw new ParamErrorException("the image pkg does not exist");
        }
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Query a list of application package menus based on the provided search
     * condition.
     *
     * @param condition The search condition for querying application package menus.
     * @return List of ApplicationPackageMenuVo representing the menu list.
     */
    @Override
    public List<ApplicationPackageMenuVo> queryPkgMenuList(final ApplicationPackageSearchCondition condition) {
        Map<String, Object> map = appPkgGateway.queryMenuByName(condition);
        Long total = (Long) map.get("total");
        List<ApplicationPackageMenuVo> menus = (List<ApplicationPackageMenuVo>) map.get("list");
        if (total == 0 || menus.size() == 0) {
            throw new NoneResException("the image package does not exist");
        }
        return menus;
    }

    /**
     * Queries all available openEuler version of application package.
     *
     * @param condition The search condition.
     * @return Map containing the epkg package menu.
     */
    @Override
    public ResponseEntity<Object> queryEulerVersionsByName(ApplicationPackageNameSearchCondition condition) {
        Map<String, Object> res = appPkgGateway.queryEulerVersionByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }
}
