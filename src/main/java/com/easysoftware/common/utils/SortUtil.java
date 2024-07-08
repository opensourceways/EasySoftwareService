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

package com.easysoftware.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.easysoftware.application.applicationpackage.vo.EulerVer;

public final class SortUtil {
    // Private constructor to prevent instantiation of the utility class
    private SortUtil() {
        // private constructor to hide the implicit public one
        throw new AssertionError("QueryWrapperUtil class cannot be instantiated.");
    }

    /**
     * The order of tags.
     */
    private static final List<String> ORDERS = List.of("FIELD", "RPM", "IMAGE", "EPKG", "OEPKG");

    /**
     * sort the tags.
     *
     * @param tags The origin tags
     * @return A list sorted
     */
    public static List<String> sortTags(Collection<String> tags) {
        List<String> list = new ArrayList<>(tags);
        Comparator<String> listCompare = (s1, s2) -> {
            Integer i1 = ORDERS.indexOf(s1);
            Integer i2 = ORDERS.indexOf(s2);
            return i1.compareTo(i2);
        };

        Collections.sort(list, listCompare);
        return list;
    }

    /**
     * sort the eulerver by os.
     * @param <T> object which implements eulerver.
     * @param list lsit of eulerver.
     * @return lsit of eulerver.
     */
    public static <T extends EulerVer> List<T> sortEulerVer(List<T> list) {
        List<T> sorted = list.stream().sorted(
            Comparator.comparing(T::getOs, Comparator.reverseOrder())
        ).collect(Collectors.toList());

        Map<Boolean, List<T>> map = sorted.stream().collect(
            Collectors.partitioningBy(e -> e.getOs().contains("preview"))
        );

        List<T> noPreview = map.get(false);
        List<T> preview = map.get(true);
        noPreview.addAll(preview);
        return noPreview;
    }
}
