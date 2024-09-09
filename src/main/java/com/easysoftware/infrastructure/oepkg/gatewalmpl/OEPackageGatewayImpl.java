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

package com.easysoftware.infrastructure.oepkg.gatewalmpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.oepackage.dto.OEPackageSearchCondition;
import com.easysoftware.application.oepackage.dto.OepkgNameSearchCondition;
import com.easysoftware.application.oepackage.vo.OEPackageDetailVo;
import com.easysoftware.application.oepackage.vo.OEPackageMenuVo;
import com.easysoftware.application.oepackage.vo.OepkgEulerVersionVo;
import com.easysoftware.application.rpmpackage.dto.RPMPackageNameSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageNewestVersionVo;
import com.easysoftware.application.rpmpackage.vo.PackgeVersionVo;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.utils.ClassField;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.common.utils.SortUtil;
import com.easysoftware.domain.oepackage.gateway.OEPackageGateway;
import com.easysoftware.infrastructure.mapper.OEPackageDOMapper;
import com.easysoftware.infrastructure.oepkg.gatewalmpl.coverter.OEPackageConverter;
import com.easysoftware.infrastructure.oepkg.gatewalmpl.dataobject.OepkgDO;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.converter.RPMPackageConverter;
import com.power.common.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OEPackageGatewayImpl implements OEPackageGateway {

    /**
     * Autowired RPMPackageDOMapper for database operations.
     */
    @Autowired
    private OEPackageDOMapper oEPkgMapper;

    /**
     * Query detailed information based on the provided search condition for
     * OEpackages.
     *
     * @param condition The search condition for querying OEpackage details
     * @return A map containing detailed information
     */
    @Override
    public Map<String, Object> queryDetailByName(final OEPackageSearchCondition condition) {
        Page<OepkgDO> page = createPage(condition);
        QueryWrapper<OepkgDO> wrapper = QueryWrapperUtil.createQueryWrapper(new OepkgDO(),
                condition, "rpm_update_at");
        IPage<OepkgDO> resPage = oEPkgMapper.selectPage(page, wrapper);
        List<OepkgDO> oepkgDOs = resPage.getRecords();

        List<OEPackageDetailVo> oeDetails = OEPackageConverter.toDetail(oepkgDOs);
        long total = resPage.getTotal();

        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", total),
                Map.entry("list", oeDetails));

        return res;
    }

    /**
     * Query menu items based on the provided search condition for OEpackages.
     *
     * @param condition The search condition for querying menu items
     * @return A map containing menu items
     */
    @Override
    public Map<String, Object> queryMenuByName(final OEPackageSearchCondition condition) {
        Page<OepkgDO> page = createPage(condition);
        QueryWrapper<OepkgDO> wrapper = QueryWrapperUtil.createQueryWrapper(new OepkgDO(),
                condition, "rpm_update_at");
        OEPackageMenuVo pkgVo = new OEPackageMenuVo();
        List<String> columns = ClassField.getFieldNames(pkgVo);
        wrapper.select(columns);
        IPage<OepkgDO> resPage = oEPkgMapper.selectPage(page, wrapper);
        List<OepkgDO> oepkgDOs = resPage.getRecords();

        List<OEPackageMenuVo> oeMenus = OEPackageConverter.toMenu(oepkgDOs);
        long total = resPage.getTotal();

        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", total),
                Map.entry("list", oeMenus));
        return res;
    }

    /**
     * Creates a Page of OEPackageDO based on the provided search condition.
     *
     * @param condition The OEPackageSearchCondition object to create the page
     *                  from.
     * @return A Page of OEPackageDO entities.
     */
    private Page<OepkgDO> createPage(final OEPackageSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        return new Page<>(pageNum, pageSize);
    }

    /**
     * Query columns based on the provided list of columns for OEpackages.
     *
     * @param columns The list of columns to query
     * @return A map containing column data
     */
    @Override
    public Map<String, List<String>> queryColumn(final List<String> columns) {
        Map<String, List<String>> res = new HashMap<>();
        for (String column : columns) {
            List<String> colList = queryColumn(column);
            colList = SortUtil.sortColumn(column, colList);
            res.put(column, colList);
        }
        return res;
    }

    /**
     * Query a specific column and return the results as a list of strings.
     *
     * @param column The name of the column to query.
     * @return A list of strings representing the queried column.
     */
    public List<String> queryColumn(final String column) {
        // 白名单列
        List<String> allowedColumns = Arrays.asList("category", "os", "arch");

        if (!allowedColumns.contains(column)) {
            throw new ParamErrorException("the value of parameter column: category, os, arch");
        }

        QueryWrapper<OepkgDO> wrapper = new QueryWrapper<>();
        // 安全地选择列，列名已经通过白名单验证
        wrapper.select("distinct " + column);
        List<OepkgDO> oepkgColumn;
        try {
            oepkgColumn = oEPkgMapper.selectList(wrapper);
        } catch (BadSqlGrammarException e) {
            throw new ParamErrorException("the value of parameter column: category, os, arch");
        }

        String underlineToCamelColumn = StringUtil.underlineToCamel(column);

        return OEPackageConverter.toColumn(oepkgColumn, underlineToCamelColumn);
    }

    /**
     * Get the total number of records in the OEpkg package table.
     *
     * @return The total number of records in the table
     */
    @Override
    public long queryTableLength() {
        QueryWrapper<OepkgDO> wrapper = new QueryWrapper<>();
        wrapper.select("distinct name");
        return oEPkgMapper.selectCount(wrapper);
    }

    /**
     * Query detailed information by package ID for OEpackages.
     *
     * @param pkgId The package ID to query detailed information
     * @return A list of OEPackageDetailVo objects
     */
    @Override
    public List<OEPackageDetailVo> queryDetailByPkgId(final String pkgId) {
        QueryWrapper<OepkgDO> wrapper = new QueryWrapper<>();
        if (pkgId != null) {
            wrapper.eq("pkg_id", pkgId);
        }
        List<OepkgDO> rpmList = oEPkgMapper.selectList(wrapper);

        return OEPackageConverter.toDetail(rpmList);
    }

    /**
     * Query the Euler Version based on the provided search condition.
     *
     * @param condition The search condition for querying a part of the RPM Euler
     *                  Version
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryEulerVersionByName(OepkgNameSearchCondition condition) {
        QueryWrapper<OepkgDO> wrapper = QueryWrapperUtil.createQueryWrapper(new OepkgDO(),
                condition, "");
        OepkgEulerVersionVo pkgVo = new OepkgEulerVersionVo();
        List<String> columns = ClassField.getFieldNames(pkgVo);
        if (condition.getName() != null) {
            wrapper.eq("name", condition.getName());
        }
        wrapper.select(columns);
        wrapper.groupBy("os", "arch");
        List<OepkgDO> rpmList = oEPkgMapper.selectList(wrapper);
        List<OepkgEulerVersionVo> versions = OEPackageConverter.toVersion(rpmList);
        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", versions.size()),
                Map.entry("list", versions));
        return res;
    }

    /**
     * query pkg num of arch by os.
     *
     * @param os os.
     * @return pkg nums of arch.
     */
    @Override
    public Map<String, Object> queryArchNum(String os) {
        LambdaQueryWrapper<OepkgDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(OepkgDO::getArch, OepkgDO::getCount);
        wrapper.groupBy(OepkgDO::getArch);
        List<OepkgDO> list = oEPkgMapper.selectList(wrapper);

        Map<String, Object> res = list.stream()
                .collect(Collectors.toMap(OepkgDO::getArch, OepkgDO::getCount));
        res.put("type", "oepkg");
        return res;
    }

    /**
     * Query the RPM newest version based on the provided search condition.
     *
     * @param condition The search condition for querying a part of the RPM
     *                  newest version
     * @return A map containing relevant information
     */
    @Override
    public List<RPMPackageNewestVersionVo> queryNewstRpmVersion(RPMPackageNameSearchCondition condition) {
        QueryWrapper<OepkgDO> wrapper = new QueryWrapper<>();
        String name = condition.getName();
        List<String> columns = ClassField.getFieldNames(new PackgeVersionVo());
        if (!StringUtils.isBlank(name)) {
            wrapper.eq("lower(name)", name.toLowerCase());
        }
        wrapper.select(columns);
        List<OepkgDO> rpmList = oEPkgMapper.selectList(wrapper);
        return RPMPackageConverter.toRPMVersionFromOepkg(rpmList);
    }
}
