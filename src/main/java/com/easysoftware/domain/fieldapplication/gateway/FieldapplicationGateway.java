package com.easysoftware.domain.fieldapplication.gateway;


import com.easysoftware.application.filedapplication.dto.FiledApplicationSerachCondition;
import com.easysoftware.application.filedapplication.vo.FiledApplicationVo;

import java.util.List;
import java.util.Map;


public interface FieldapplicationGateway {
    /**
     * Query menu items by page based on the specified search condition.
     *
     * @param condition The search condition for querying menu items.
     * @return A map containing menu items based on the page and condition.
     */
    Map<String, Object> queryMenuByPage(FiledApplicationSerachCondition condition);

    /**
     * Query a list of FiledApplicationVo objects.
     *
     * @return A list of FiledApplicationVo objects.
     */
    List<FiledApplicationVo> queryVoList();

    /**
     * Query columns and their values based on the specified list of columns.
     *
     * @param columns The list of columns to query.
     * @return A map containing column names as keys and lists of values as values.
     */
    Map<String, List<String>> queryColumn(List<String> columns);


}
