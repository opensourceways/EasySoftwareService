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
package com.easysoftware.application.rpmpackage.vo;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RPMPackageNewestVersionVo {
    /**
     * Newest versions of the rpm package.
     */
    private String newestVersion;

    /**
     * The coresspond euler versions of the rpm package.
     */
    private String os;

    /**
     * pick one from list.
     * @param list list.
     * @return RPMPackageNewestVersionVo containg latest newestVersion.
     */
    public static RPMPackageNewestVersionVo pickNewestOne(List<RPMPackageNewestVersionVo> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        List<RPMPackageNewestVersionVo> sortedList =  list.stream()
                .sorted(
                    Comparator.comparing(RPMPackageNewestVersionVo::getOs)
                    .thenComparing(RPMPackageNewestVersionVo::getNewestVersion)
                )
                .collect(Collectors.toList());

        return sortedList.get(sortedList.size() - 1);
    }
}
