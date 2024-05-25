package com.easysoftware.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class SortUtil {
    // Private constructor to prevent instantiation of the utility class
    private SortUtil() {
        // private constructor to hide the implicit public one
        throw new AssertionError("QueryWrapperUtil class cannot be instantiated.");
    }

    /**
     * The order of tags.
     */
    private static final List<String> ORDERS = List.of("RPM", "IMAGE", "EPKG");

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
}
