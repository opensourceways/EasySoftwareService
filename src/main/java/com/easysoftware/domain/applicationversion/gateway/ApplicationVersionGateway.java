package com.easysoftware.domain.applicationversion.gateway;

import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
import java.util.List;
import java.util.Map;

public interface ApplicationVersionGateway {

    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying application versions
     * @return A map containing relevant information
     */
    Map<String, Object> queryByName(ApplicationVersionSearchCondition condition);

    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying application versions
     * @return A map containing relevant information
     */
    Map<String, Object> queryByEulerOsVersion(ApplicationVersionSearchCondition condition);

    /**
     * Search column.
     *
     * @param columns columns
     * @return A collection of ApplicationVersionDO objects
     */
    Map<String, List<String>> queryColumn(List<String> columns);
}
