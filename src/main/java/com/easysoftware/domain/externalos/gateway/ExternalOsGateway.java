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

package com.easysoftware.domain.externalos.gateway;

import com.easysoftware.application.externalos.dto.ExternalOsSearchCondiiton;
import com.easysoftware.domain.externalos.ExternalOsUnique;

import java.util.Map;

public interface ExternalOsGateway {
    /**
     * Query package mapping based on the provided search condition.
     *
     * @param condition The search condition for querying package mappings
     * @return A map containing relevant information
     */
    Map<String, Object> queryPkgMap(ExternalOsSearchCondiiton condition);

    /**
     * Check if an external operating system exists based on its ID.
     *
     * @param id The ID of the external operating system
     * @return true if the external operating system exists, false otherwise
     */
    boolean existExternalOs(String id);

    /**
     * Check if an external operating system exists based on its unique identifier.
     *
     * @param uni The unique identifier of the external operating system
     * @return true if the external operating system exists, false otherwise
     */
    boolean existExternalOs(ExternalOsUnique uni);
}
