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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.easysoftware.infrastructure.archnum.dataobject.ArchNumDO;

@Component
public class ArchNumConverter {
    /**
     * convert list of ArchNumDO to map.
     * @param list list of ArchNumDO.
     * @return map.
     */
    public Map<String, Map<String, Map<String, Integer>>> toMap(List<ArchNumDO> list) {
        return list.stream().collect(
            Collectors.groupingBy(
                ArchNumDO::getOs, Collectors.groupingBy(
                    ArchNumDO::getType, Collectors.toMap(
                        ArchNumDO::getArchName, ArchNumDO::getCount)
                )
            )
        );
    }
}
