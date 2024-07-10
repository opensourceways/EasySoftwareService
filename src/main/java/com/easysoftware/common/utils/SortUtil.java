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
import com.easysoftware.application.filedapplication.vo.EulerLifeCycleVo;

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
     * sort the EulerLifeCycleVo.
     *
     * @param eulerLifeCycleVo The origin eulerLifeCycleVo
     * @return A list sorted
     */
    public static List<EulerLifeCycleVo> sortEulerLifeCycleVo(List<EulerLifeCycleVo> eulerLifeCycleVo) {
        List<EulerLifeCycleVo> sortedElVos = eulerLifeCycleVo.stream()
                .sorted(Comparator.comparing(EulerLifeCycleVo::getStatus, Comparator.reverseOrder())
                        .thenComparing(EulerLifeCycleVo::getOs))
                .collect(Collectors.toList());
        return sortedElVos;
    }

    /**
     * sort the eulerver by os.
     *
     * @param <T>  object which implements eulerver.
     * @param list lsit of eulerver.
     * @return lsit of eulerver.
     */
    public static <T extends EulerVer> List<T> sortEulerVer(List<T> list) {
        List<T> sorted = list.stream().sorted(
                Comparator.comparing(T::getOs, Comparator.reverseOrder())).collect(Collectors.toList());

        Map<Boolean, List<T>> map = sorted.stream().collect(
                Collectors.partitioningBy(e -> e.getOs().contains("preview")));

        List<T> noPreview = map.get(false);
        List<T> preview = map.get(true);
        noPreview.addAll(preview);
        return noPreview;
    }

    /**
     * sort the list.
     * @param column column.
     * @param coll list.
     * @return sorted list.
     */
    public static List<String> sortColumn(String column, Collection<String> coll) {
        if (column == null || coll == null || coll.isEmpty()) {
            return Collections.emptyList();
        }

        if ("os".equals(column)) {
            return SortUtil.sortOsColumn(coll);
        } else if ("category".equals(column)) {
            return SortUtil.sortCategoryColumn(coll);
        } else if ("arch".equals(column)) {
            return SortUtil.sortArch(coll);
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Sorts the list of strings representing operating systems in ascending order.
     *
     * @param colList The list of strings representing operating systems to be sorted
     * @return A sorted list of strings representing operating systems in ascending order
     */
    public static List<String> sortOsColumn(Collection<String> colList) {
        if (colList == null || colList.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Boolean, List<String>> partMap = colList.stream()
                .collect(Collectors.partitioningBy(e -> !e.contains("preview")));
        List<String> con = partMap.get(true);
        List<String> don = partMap.get(false);
        Collections.sort(con, Collections.reverseOrder());
        con.addAll(don);
        return con;
    }

    /**
     * Sorts the list of strings representing category in ascending order.
     *
     * @param colList The list of strings representing category to be sorted
     * @return A sorted list of strings representing category in ascending order
     */
    public static List<String> sortCategoryColumn(Collection<String> colList) {
        if (colList == null || colList.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> list = colList.stream().sorted().collect(Collectors.toList());
        if (list.contains("其他")) {
            list.remove("其他");
            list.add("其他");
        }
        return list;
    }

    /**
     * sort arch.
     * @param list origin list.
     * @return sorted list.
     */
    public static List<String> sortArch(Collection<String> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        return list.stream().sorted().collect(Collectors.toList());
    }
}
