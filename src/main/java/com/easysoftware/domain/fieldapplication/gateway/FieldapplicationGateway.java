package com.easysoftware.domain.fieldapplication.gateway;


import com.easysoftware.application.filedapplication.dto.FiledApplicationSerachCondition;

import java.util.List;
import java.util.Map;


public interface FieldapplicationGateway {
    /**
     * Query all based on the provided search condition for filed applications.
     *
     * @param condition The search condition for filed applications
     * @return A map containing relevant information
     */
    Map<String, Object> queryAll(FiledApplicationSerachCondition condition);

    /**
     * Query columns based on the provided list of columns.
     *
     * @param columns The list of columns to query
     * @return A map containing column data as lists of strings
     */
    Map<String, List<String>> queryColumn(List<String> columns);


}
