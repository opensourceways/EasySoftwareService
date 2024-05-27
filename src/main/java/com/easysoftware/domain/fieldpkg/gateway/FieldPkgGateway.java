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
