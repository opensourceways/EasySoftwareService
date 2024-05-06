package com.easysoftware.common.utils;

import java.util.UUID;

public final class UuidUtil {

    // Private constructor to prevent instantiation of the utility class
    private UuidUtil() {
        // private constructor to hide the implicit public one
        throw new AssertionError("UuidUtil class cannot be instantiated.");
    }

    /**
     * Generate a UUID with 32 characters.
     *
     * @return A UUID string of length 32
     */
    public static String getUUID32() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

}
