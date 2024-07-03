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

     /**
      * Query statistics.
      * @return ResponseEntity object containing the statistical information.
      */
    ResponseEntity<Object> searchArchNumByOs();
}
