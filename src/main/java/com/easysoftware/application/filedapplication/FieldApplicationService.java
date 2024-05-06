package com.easysoftware.application.filedapplication;

import org.springframework.http.ResponseEntity;

import com.easysoftware.application.filedapplication.dto.FieldColumnSearchCondition;
import com.easysoftware.application.filedapplication.dto.FieldDetailSearchCondition;
import com.easysoftware.application.filedapplication.dto.FiledApplicationSerachCondition;

public interface FieldApplicationService {
    /**
     * Query menu by name.
     *
     * @param condition The search condition for querying the menu.
     * @return ResponseEntity object containing the query results.
     */
    ResponseEntity<Object> queryMenuByName(FiledApplicationSerachCondition condition);

    /**
     * Query detail by name.
     *
     * @param condition The search condition for querying the detail.
     * @return ResponseEntity object containing the query results.
     */
    ResponseEntity<Object> queryDetailByName(FieldDetailSearchCondition condition);

    /**
     * Search column.
     *
     * @param condition The search condition for column search.
     * @return ResponseEntity object containing the search results.
     */
    ResponseEntity<Object> searchColumn(FieldColumnSearchCondition condition);

    /**
     * Query statistics.
     *
     * @return ResponseEntity object containing the statistical information.
     */
    ResponseEntity<Object> queryStat();


}
