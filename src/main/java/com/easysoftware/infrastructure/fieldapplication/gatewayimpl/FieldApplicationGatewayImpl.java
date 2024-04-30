package com.easysoftware.infrastructure.fieldapplication.gatewayimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.filedapplication.dto.FiledApplicationSerachCondition;
import com.easysoftware.application.filedapplication.vo.FiledApplicationVo;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.domain.fieldapplication.gateway.FieldapplicationGateway;
import com.easysoftware.infrastructure.fieldapplication.gatewayimpl.converter.FieldApplicationConverter;
import com.easysoftware.infrastructure.fieldapplication.gatewayimpl.dataobject.FieldApplicationDO;
import com.easysoftware.infrastructure.mapper.FieldApplicationDOMapper;
import com.power.common.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FieldApplicationGatewayImpl implements FieldapplicationGateway {
    @Autowired
    private FieldApplicationDOMapper fieldAppMapper;

    private static final Logger logger = LoggerFactory.getLogger(FieldApplicationGatewayImpl.class);

    @Override
    public Map<String, Object> queryAll(FiledApplicationSerachCondition condition) {
        Page<FieldApplicationDO> page = createPage(condition);
        QueryWrapper<FieldApplicationDO> wrapper = QueryWrapperUtil.createQueryWrapper(new FieldApplicationDO(),
                condition, null);
        IPage<FieldApplicationDO> resPage = fieldAppMapper.selectPage(page, wrapper);
        List<FieldApplicationDO> list = resPage.getRecords();
        List<FiledApplicationVo> voList = FieldApplicationConverter.toVo(list);
        long total = resPage.getTotal();

        return Map.ofEntries(
                Map.entry("total", total),
                Map.entry("list", voList)
        );
    }

    private Page<FieldApplicationDO> createPage(FiledApplicationSerachCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        Page<FieldApplicationDO> page = new Page<>(pageNum, pageSize);
        return page;
    }

    @Override
    public Map<String, List<String>> queryColumn(List<String> columns) {
        Map<String, List<String>> res = new HashMap<>();
        for (String column : columns) {
            List<String> colList = queryColumn(column);
            res.put(column, colList);
        }
        return res;
    }

    public List<String> queryColumn(String column) {
        // 白名单列
        List<String> allowedColumns = Arrays.asList("category", "os", "arch"); 

        if (!allowedColumns.contains(column)) {  
            throw new ParamErrorException("Unsupported column: " + column);  
        }

        QueryWrapper<FieldApplicationDO> wrapper = new QueryWrapper<>();
        // 安全地选择列，列名已经通过白名单验证
        wrapper.select("distinct " + column);
        List<FieldApplicationDO> columnList = new ArrayList<>();
        try {
            columnList = fieldAppMapper.selectList(wrapper);
        } catch (BadSqlGrammarException e) {
            throw new ParamErrorException("unsupported param: " + column);
        }

        column = StringUtil.underlineToCamel(column);
        return FieldApplicationConverter.toColumn(columnList, column);
    }
}
