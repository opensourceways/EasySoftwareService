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

package com.easysoftware.infrastructure.epkgpackage.gatewayimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageNameSearchCondition;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageDetailVo;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageEulerVersionVo;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageMenuVo;
import com.easysoftware.common.exception.NoneResException;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.utils.ClassField;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.common.utils.SortUtil;
import com.easysoftware.domain.epkgpackage.gateway.EPKGPackageGateway;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.converter.EPKGPackageConverter;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.dataobject.EPKGPackageDO;
import com.easysoftware.infrastructure.mapper.EPKGPackageDOMapper;
import com.power.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EPKGPackageGatewayImpl implements EPKGPackageGateway {

    /**
     * Autowired EPKGPackageDOMapper for database operations.
     */
    @Autowired
    private EPKGPackageDOMapper ePKGPkgMapper;

    /**
     * Query detailed information based on the provided search condition.
     *
     * @param condition The search condition for querying EPKG package details
     * @return A map containing detailed information
     */
    @Override
    public Map<String, Object> queryDetailByName(final EPKGPackageSearchCondition condition) {
        Page<EPKGPackageDO> page = createPage(condition);
        QueryWrapper<EPKGPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new EPKGPackageDO(),
                condition, "epkg_update_at");
        IPage<EPKGPackageDO> resPage = ePKGPkgMapper.selectPage(page, wrapper);
        List<EPKGPackageDO> rPMDOs = resPage.getRecords();
        List<EPKGPackageDetailVo> rPMDetails = EPKGPackageConverter.toDetail(rPMDOs);
        long total = resPage.getTotal();

        if (total == 0 || rPMDetails.size() == 0) {
            throw new NoneResException("the epkg package does not exist");
        }

        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", total),
                Map.entry("list", rPMDetails));

        return res;
    }

    /**
     * Query menu items based on the provided search condition.
     *
     * @param condition The search condition for querying menu items
     * @return A map containing menu items
     */
    @Override
    public Map<String, Object> queryMenuByName(final EPKGPackageSearchCondition condition) {
        Page<EPKGPackageDO> page = createPage(condition);
        QueryWrapper<EPKGPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new EPKGPackageDO(),
                condition, "epkg_update_at");
        EPKGPackageMenuVo pkgVo = new EPKGPackageMenuVo();
        List<String> columns = ClassField.getFieldNames(pkgVo);
        wrapper.select(columns);
        IPage<EPKGPackageDO> resPage = ePKGPkgMapper.selectPage(page, wrapper);
        List<EPKGPackageDO> rpmDOs = resPage.getRecords();
        List<EPKGPackageMenuVo> rPMMenus = EPKGPackageConverter.toMenu(rpmDOs);
        long total = resPage.getTotal();

        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", total),
                Map.entry("list", rPMMenus));
        return res;
    }

    /**
     * Creates a Page of EPKGPackageDO based on the provided search condition.
     *
     * @param condition The EPKGPackageSearchCondition object to create the page
     *                  from.
     * @return A Page of EPKGPackageDO entities.
     */
    private Page<EPKGPackageDO> createPage(final EPKGPackageSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        Page<EPKGPackageDO> page = new Page<>(pageNum, pageSize);
        return page;
    }

    /**
     * Query columns based on the provided list of columns.
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

        QueryWrapper<EPKGPackageDO> wrapper = new QueryWrapper<>();
        // 安全地选择列，列名已经通过白名单验证
        wrapper.select("distinct " + column);
        List<EPKGPackageDO> rpmColumn;
        try {
            rpmColumn = ePKGPkgMapper.selectList(wrapper);
        } catch (BadSqlGrammarException e) {
            throw new ParamErrorException("the value of parameter column: category, os, arch");
        }
        String underlineToCamelColumn = StringUtil.underlineToCamel(column);

        return EPKGPackageConverter.toColumn(rpmColumn, underlineToCamelColumn);
    }

    /**
     * Get the total number of records in the table.
     *
     * @return The total number of records in the table
     */
    @Override
    public long queryTableLength() {
        return ePKGPkgMapper.selectCount(null);
    }

    /**
     * Select a single EPKGPackageMenuVo object by name.
     *
     * @param name The name used to select the object
     * @return The selected EPKGPackageMenuVo object
     */
    @Override
    public EPKGPackageMenuVo selectOne(final String name) {
        QueryWrapper<EPKGPackageDO> wrapper = new QueryWrapper<>();
        wrapper.select("pkg_id");
        if (name != null) {
            wrapper.eq("name", name);
        }
        wrapper.last("order by epkg_update_at desc limit 1");
        List<EPKGPackageDO> epkgList = ePKGPkgMapper.selectList(wrapper);
        if (epkgList.size() == 0) {
            return new EPKGPackageMenuVo();
        }
        return EPKGPackageConverter.toMenu(epkgList.get(0));
    }

    /**
     * Query detailed information by package ID.
     *
     * @param pkgId The package ID to query detailed information
     * @return A list of EPKGPackageDetailVo objects
     */
    @Override
    public List<EPKGPackageDetailVo> queryDetailByPkgId(final String pkgId) {
        QueryWrapper<EPKGPackageDO> wrapper = new QueryWrapper<>();
        wrapper.eq("pkg_id", pkgId);
        List<EPKGPackageDO> epkgList = ePKGPkgMapper.selectList(wrapper);
        return EPKGPackageConverter.toDetail(epkgList);
    }

    /**
     * Query the Euler Version based on the provided search condition.
     *
     * @param condition The search condition for querying a part of the epkg Euler
     *                  Version
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryEulerVersionByName(final EPKGPackageNameSearchCondition condition) {
        QueryWrapper<EPKGPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new EPKGPackageDO(),
                condition, "");
        EPKGPackageEulerVersionVo pkgVo = new EPKGPackageEulerVersionVo();
        List<String> columns = ClassField.getFieldNames(pkgVo);
        if (condition.getName() != null) {
            wrapper.eq("name", condition.getName());
        }
        wrapper.select(columns);
        wrapper.groupBy("os", "arch");
        List<EPKGPackageDO> epkgList = ePKGPkgMapper.selectList(wrapper);
        List<EPKGPackageEulerVersionVo> versions = EPKGPackageConverter.toVersion(epkgList);
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
        LambdaQueryWrapper<EPKGPackageDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(EPKGPackageDO::getArch, EPKGPackageDO::getCount);
        wrapper.groupBy(EPKGPackageDO::getArch);
        List<EPKGPackageDO> list = ePKGPkgMapper.selectList(wrapper);

        Map<String, Object> res = list.stream()
                .collect(Collectors.toMap(EPKGPackageDO::getArch, EPKGPackageDO::getCount));
        res.put("type", "epkg");
        return res;
    }
}
