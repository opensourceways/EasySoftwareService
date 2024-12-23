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

package com.easysoftware.infrastructure.applicationversion.gatewayimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
import com.easysoftware.application.rpmpackage.vo.PackgeVersionVo;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.common.utils.SortUtil;
import com.easysoftware.domain.applicationversion.ApplicationVersion;
import com.easysoftware.domain.applicationversion.gateway.ApplicationVersionGateway;
import com.easysoftware.infrastructure.applicationversion.gatewayimpl.converter.ApplicationVersionConvertor;
import com.easysoftware.infrastructure.applicationversion.gatewayimpl.dataobject.ApplicationVersionDO;
import com.easysoftware.infrastructure.mapper.ApplicationVersionDOMapper;
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
public class ApplicationVersionGatewayImpl implements ApplicationVersionGateway {

    /**
     * Autowired field for the ApplicationVersionDOMapper.
     */
    @Autowired
    private ApplicationVersionDOMapper appVersionMapper;

    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying application versions
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryByName(final ApplicationVersionSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        String name = condition.getName();

        Page<ApplicationVersionDO> page = new Page<>(pageNum, pageSize);

        QueryWrapper<ApplicationVersionDO> wrapper = new QueryWrapper<>();
        if (name != null) {
            wrapper.eq("name", name);
        }
        Page<ApplicationVersionDO> resPage = appVersionMapper.selectPage(page, wrapper);
        List<ApplicationVersionDO> appDOs = resPage.getRecords();
        List<ApplicationVersion> appDetails = ApplicationVersionConvertor.toEntity(appDOs);

        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", resPage.getTotal()),
                Map.entry("list", appDetails));

        return res;
    }

    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying application versions
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryByEulerOsVersion(ApplicationVersionSearchCondition condition) {
        Page<ApplicationVersionDO> page = this.<ApplicationVersionDO>createPage(condition);

        QueryWrapper<ApplicationVersionDO> wrapper = QueryWrapperUtil.createQueryWrapper(new ApplicationVersionDO(),
                condition, "");

        Page<ApplicationVersionDO> resPage = appVersionMapper.selectPage(page, wrapper);
        List<ApplicationVersionDO> appList = resPage.getRecords();
        List<ApplicationVersion> appDetails = ApplicationVersionConvertor.toEntity(appList);
        return Map.ofEntries(
                Map.entry("total", resPage.getTotal()),
                Map.entry("list", appDetails));
    }

    private <T> Page<T> createPage(ApplicationVersionSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        return new Page<T>(pageNum, pageSize);
    }

    /**
     * Query column.
     *
     * @param columns columns
     * @return A map containing relevant information
     */
    @Override
    public Map<String, List<String>> queryColumn(List<String> columns) {
        Map<String, List<String>> res = new HashMap<>();
        for (String column : columns) {
            List<String> colList = queryColumn(column);
            if ("eulerOsVersion".equals(column)) {
                colList = SortUtil.sortOsColumn(colList);
            }
            res.put(column, colList);
        }
        return res;
    }

    private List<String> queryColumn(String column) {
        List<String> allowedColumns = Arrays.asList("eulerOsVersion", "type", "status");
        if (!allowedColumns.contains(column)) {
            throw new ParamErrorException("the value of parameter column: eulerOsVersion");
        }

        QueryWrapper<ApplicationVersionDO> wrapper = new QueryWrapper<>();
        wrapper.select("distinct " + StringUtil.camelToUnderline(column));
        List<ApplicationVersionDO> columnList;
        try {
            columnList = appVersionMapper.selectList(wrapper);
        } catch (BadSqlGrammarException e) {
            throw new ParamErrorException("the value of parameter column: eulerOsVersion");
        }

        String underlineToCamelColumn = StringUtil.underlineToCamel(column);
        return ApplicationVersionConvertor.toColumn(columnList, underlineToCamelColumn);
    }

    /**
     * Query all upstream package version.
     *
     * @return A map containing relevant information
     */
    @Override
    public Map<String, List<PackgeVersionVo>> queryUpstreamVersions() {
        LambdaQueryWrapper<ApplicationVersionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(ApplicationVersionDO::getName,
                       ApplicationVersionDO::getUpstreamVersion,
                       ApplicationVersionDO::getEulerOsVersion);
        List<ApplicationVersionDO> doList = appVersionMapper.selectList(wrapper);
        List<PackgeVersionVo> voList = ApplicationVersionConvertor.toPackgeVersionVo(doList);
        Map<String, List<PackgeVersionVo>> res = voList.stream().
                                                 collect(Collectors.groupingBy(PackgeVersionVo::getName));
        return res;
    }
}
