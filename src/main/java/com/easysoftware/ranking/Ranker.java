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

package com.easysoftware.ranking;

import java.util.Map;
import java.util.List;


public interface Ranker {
    /**
     * Rank domain pages based on operation configuration.
     *
     * @param domainPage List of domain pages as maps to rank.
     * @return Ranked list of domain pages.
     */
    List<Map<String, Object>> rankingDomainPageByOperationConfig(List<Map<String, Object>> domainPage);

    /**
     * Rank domain pages based on algorithm.
     *
     * @param domainPage List of domain pages as maps to rank.
     * @return Ranked list of domain pages.
     */
    List<Map<String, Object>> rankingDomainPageByAlgorithm(List<Map<String, Object>> domainPage);

}
