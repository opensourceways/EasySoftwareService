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
package com.easysoftware.application.oepackage;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.easysoftware.application.oepackage.dto.OEPackageSearchCondition;
import com.easysoftware.application.oepackage.dto.OepkgNameSearchCondition;
import com.easysoftware.application.oepackage.vo.OEPackageDetailVo;
import com.easysoftware.common.exception.NoneResException;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.oepackage.gateway.OEPackageGateway;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class OEPackageServiceImpl implements OEPackageService {

    /**
     * Resource for OEPackage Gateway.
     */
    @Resource
    private OEPackageGateway oEPkgGateway;

    /**
     * Searches for OEpackages.
     *
     * @param condition The search condition.
     * @return ResponseEntity with the search results.
     */
    @Override
    public ResponseEntity<Object> searchOEPkg(final OEPackageSearchCondition condition) {
        if (StringUtils.isBlank(condition.getPkgId())) {
            throw new ParamErrorException("the pkgid can not be null");
        }

        List<OEPackageDetailVo> oepList = oEPkgGateway.queryDetailByPkgId(condition.getPkgId());
        if (oepList.isEmpty()) {
            throw new NoneResException("the oe package does not exist");
        }
        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", oepList.size()),
                Map.entry("list", oepList));
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Queries all OEpackage menus.
     *
     * @param condition The search condition.
     * @return Map containing the OEpackage menu.
     */
    @Override
    public Map<String, Object> queryAllOEPkgMenu(final OEPackageSearchCondition condition) {
        return oEPkgGateway.queryMenuByName(condition);
    }

    /**
     * Queries all available openEuler version of oepkg package.
     *
     * @param condition The search condition.
     * @return Map containing the epkg package menu.
     */
    @Override
    public ResponseEntity<Object> queryEulerVersionsByName(OepkgNameSearchCondition condition) {
        Map<String, Object> res = oEPkgGateway.queryEulerVersionByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }
}
