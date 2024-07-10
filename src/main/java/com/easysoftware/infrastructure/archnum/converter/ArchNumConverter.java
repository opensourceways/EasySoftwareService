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

package com.easysoftware.infrastructure.archnum.converter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.easysoftware.common.utils.SortUtil;
import com.easysoftware.infrastructure.archnum.dataobject.ArchNumDO;

@Component
public class ArchNumConverter {
    /**
     * convert list of ArchNumDO to map.
     * @param list list of ArchNumDO.
     * @return map.
     */
    public Map<String, Map<String, Map<String, Integer>>> toMap(List<ArchNumDO> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, List<ArchNumDO>> osMap = list.stream().collect(Collectors.groupingBy(ArchNumDO::getOs));
        List<String> orderedOses = SortUtil.sortOsColumn(osMap.keySet());
        Map<String, Map<String, Map<String, Integer>>> res = new LinkedHashMap<>();
        for (String orderedOs: orderedOses) {
            List<ArchNumDO> aList = osMap.get(orderedOs);
            Map<String, Map<String, Integer>> typeMap = toMapPerOs(aList);
            res.put(orderedOs, typeMap);
        }
        return res;
    }

    /**
     * convert list of ArchNumDO to map for each os.
     * @param list list of ArchNumDO.
     * @return map.
     */
    public Map<String, Map<String, Integer>> toMapPerOs(List<ArchNumDO> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, List<ArchNumDO>> typeMap = list.stream().collect(Collectors.groupingBy(ArchNumDO::getType));
        List<String> orderedTypes = SortUtil.sortTags(typeMap.keySet());
        Map<String, Map<String, Integer>> res = new LinkedHashMap<>();
        for (String orderedType : orderedTypes) {
            List<ArchNumDO> aList = typeMap.get(orderedType);
            Map<String, Integer> aMap = aList.stream().collect(
                Collectors.toMap(ArchNumDO::getArchName, ArchNumDO::getCount)
            );
            res.put(orderedType, aMap);
        }
        return res;
    }
}
