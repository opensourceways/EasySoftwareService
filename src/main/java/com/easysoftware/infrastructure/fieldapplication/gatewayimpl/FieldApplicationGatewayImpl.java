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

package com.easysoftware.infrastructure.fieldapplication.gatewayimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.filedapplication.dto.FiledApplicationSerachCondition;
import com.easysoftware.application.filedapplication.vo.FiledApplicationVo;
import com.easysoftware.common.exception.NoneResException;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.domain.fieldapplication.gateway.FieldapplicationGateway;
import com.easysoftware.infrastructure.fieldapplication.gatewayimpl.converter.FieldApplicationConverter;
import com.easysoftware.infrastructure.fieldapplication.gatewayimpl.dataobject.FieldApplicationDO;
import com.easysoftware.infrastructure.mapper.FieldApplicationDOMapper;
import com.power.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class FieldApplicationGatewayImpl implements FieldapplicationGateway {

    /**
     * Autowired FieldApplicationDOMapper for database operations.
     */
    @Autowired
    private FieldApplicationDOMapper fieldAppMapper;

    /**
     * Query menu items by page based on the specified search condition.
     *
     * @param condition The search condition for querying menu items.
     * @return A map containing menu items based on the page and condition.
     */
    @Override
    public Map<String, Object> queryMenuByPage(FiledApplicationSerachCondition condition) {
        Page<FieldApplicationDO> page = createPage(condition);
        QueryWrapper<FieldApplicationDO> wrapper = QueryWrapperUtil.createQueryWrapper(new FieldApplicationDO(),
                condition, null);
        IPage<FieldApplicationDO> resPage = fieldAppMapper.selectPage(page, wrapper);
        List<FieldApplicationDO> list = resPage.getRecords();
        List<FiledApplicationVo> voList = FieldApplicationConverter.toVo(list);
        long total = resPage.getTotal();

        if (total == 0 || voList.size() == 0) {
            throw new NoneResException("the field package does not exist");
        }

        return Map.ofEntries(
                Map.entry("total", total),
                Map.entry("list", voList));
    }

    /**
     * Creates a Page of FieldApplicationDO based on the provided search condition.
     *
     * @param condition The FieldApplicationSearchCondition object to create the
     *                  page from.
     * @return A Page of FieldApplicationDO entities.
     */
    private Page<FieldApplicationDO> createPage(final FiledApplicationSerachCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        Page<FieldApplicationDO> page = new Page<>(pageNum, pageSize);
        return page;
    }

    /**
     * Query columns and their values based on the specified list of columns.
     *
     * @param columns The list of columns to query.
     * @return A map containing column names as keys and lists of values as values.
     */
    @Override
    public Map<String, List<String>> queryColumn(final List<String> columns) {
        Map<String, List<String>> res = new HashMap<>();
        for (String column : columns) {
            List<String> colList = queryColumn(column);
            if ("os".equals(column)) {
                colList = QueryWrapperUtil.sortOsColumn(colList);
            }
            if ("category".equals(column)) {
                colList = QueryWrapperUtil.sortCategoryColumn(colList);
            }
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

        QueryWrapper<FieldApplicationDO> wrapper = new QueryWrapper<>();
        // 安全地选择列，列名已经通过白名单验证
        wrapper.select("distinct " + column);
        List<FieldApplicationDO> columnList;
        try {
            columnList = fieldAppMapper.selectList(wrapper);
        } catch (BadSqlGrammarException e) {
            throw new ParamErrorException("the value of parameter column: category, os, arch");
        }

        String underlineToCamelColumn = StringUtil.underlineToCamel(column);
        return FieldApplicationConverter.toColumn(columnList, underlineToCamelColumn);
    }

    /**
     * Query a list of FiledApplicationVo objects.
     *
     * @return A list of FiledApplicationVo objects.
     */
    @Override
    public List<FiledApplicationVo> queryVoList() {
        List<FieldApplicationDO> doList = fieldAppMapper.selectList(null);
        return FieldApplicationConverter.toVo(doList);
    }
}
