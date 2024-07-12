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

package com.easysoftware.infrastructure.fieldpkg;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.fieldpkg.dto.FieldPkgSearchCondition;
import com.easysoftware.application.fieldpkg.vo.FieldPkgVo;
import com.easysoftware.common.constant.PackageConstant;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.utils.ClassField;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.common.utils.SortUtil;
import com.easysoftware.domain.fieldpkg.gateway.FieldPkgGateway;
import com.easysoftware.infrastructure.fieldpkg.converter.FieldPkgConverter;
import com.easysoftware.infrastructure.fieldpkg.dataobject.FieldPkgDO;
import com.easysoftware.infrastructure.mapper.FieldPkgDOMapper;
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
public class FieldPkgGatewayImpl implements FieldPkgGateway {
    /**
     * Autowired FieldApplicationDOMapper for database operations.
     */
    @Autowired
    private FieldPkgDOMapper mapper;

    /**
     * Query menu items by page based on the specified search condition.
     *
     * @param condition The search condition for querying menu items.
     * @return A map containing menu items based on the page and condition.
     */
    @Override
    public Map<String, Object> queryMenuByPage(final FieldPkgSearchCondition condition) {
        Page<FieldPkgDO> page = createPage(condition);
        QueryWrapper<FieldPkgDO> wrapper = QueryWrapperUtil.createQueryWrapper(new FieldPkgDO(),
                condition, null);
        List<String> columns = ClassField.getFieldNames(new FieldPkgDO());
        columns.remove("count");
        wrapper.select(columns);
        wrapper.orderByDesc("length(tags)");
        IPage<FieldPkgDO> resPage = mapper.selectPage(page, wrapper);
        List<FieldPkgDO> list = resPage.getRecords();
        List<FieldPkgVo> voList = FieldPkgConverter.toVo(list);

        if (condition.getOs() == null && condition.getArch() == null) {
            voList = FieldPkgConverter.aggregateVoByName(voList);
        }

        // 聚合后个数少于约定数目 查询下一页填充后聚合返回
        if (voList.size() < condition.getPageSize()) {
            Page<FieldPkgDO> nextPage = new Page<>(condition.getPageNum() + 1, condition.getPageSize());
            IPage<FieldPkgDO> nextResPage = mapper.selectPage(nextPage, wrapper);
            List<FieldPkgDO> nextPlist = nextResPage.getRecords();
            List<FieldPkgVo> nextVoList = FieldPkgConverter.toVo(nextPlist);
            List<FieldPkgVo> candidateVo = FieldPkgConverter.aggregateVoByName(nextVoList);
            int missNum = condition.getPageSize() - voList.size();
            for (FieldPkgVo vo : candidateVo) {
                if (missNum <= PackageConstant.ZERO) {
                    break;
                }
                voList.add(vo);
                missNum--;
            }
        }

        long total = voList.size();
        return Map.ofEntries(
                Map.entry("total", total),
                Map.entry("list", voList));
    }

    /**
     * Creates a Page of FieldPkgDO based on the provided search condition.
     *
     * @param condition The FieldPkgSearchCondition object to create the page from.
     * @return A Page of FieldPkgDO entities.
     */
    private Page<FieldPkgDO> createPage(final FieldPkgSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        Page<FieldPkgDO> page = new Page<>(pageNum, pageSize);
        return page;
    }

    /**
     * queryColumn as a list of strings.
     *
     * @param columns The name of the columns to query.
     * @return A Map of strings representing the queried column.
     */
    @Override
    public Map<String, List<String>> queryColumn(List<String> columns) {
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

        QueryWrapper<FieldPkgDO> wrapper = new QueryWrapper<>();
        // 安全地选择列，列名已经通过白名单验证
        wrapper.select("distinct " + column);
        List<FieldPkgDO> columnList;
        try {
            columnList = mapper.selectList(wrapper);
        } catch (BadSqlGrammarException e) {
            throw new ParamErrorException("the value of parameter column: category, os, arch");
        }

        String underlineToCamelColumn = StringUtil.underlineToCamel(column);
        return FieldPkgConverter.toColumn(columnList, underlineToCamelColumn);
    }

    /**
     * query pkg num of arch by os.
     *
     * @param os os.
     * @return pkg nums of arch.
     */
    @Override
    public Map<String, Object> queryArchNum(String os) {
        LambdaQueryWrapper<FieldPkgDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(FieldPkgDO::getArch, FieldPkgDO::getCount);
        wrapper.groupBy(FieldPkgDO::getArch);
        List<FieldPkgDO> list = mapper.selectList(wrapper);

        Map<String, Object> res = list.stream()
                .collect(Collectors.toMap(FieldPkgDO::getArch, FieldPkgDO::getCount));
        res.put("type", "field");
        return res;
    }

}
