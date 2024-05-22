package com.easysoftware.domain.fieldpkg.gateway;

import java.util.List;
import java.util.Map;

import com.easysoftware.application.fieldpkg.dto.FieldPkgSearchCondition;

public interface FieldPkgGateway {
    /**
     * Query menu items by page based on the specified search condition.
     *
     * @param condition The search condition for querying menu items.
     * @return A map containing menu items based on the page and condition.
     */
    Map<String, Object> queryMenuByPage(FieldPkgSearchCondition condition);

    /**
     * Query columns and their values based on the specified list of columns.
     *
     * @param columns The list of columns to query.
     * @return A map containing column names as keys and lists of values as values.
     */
    Map<String, List<String>> queryColumn(List<String> columns);

}
