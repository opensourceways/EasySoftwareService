package com.easysoftware.common.constant;

public final class PackageConstant {

    // Private constructor to prevent instantiation of the PackageConstant class
    private PackageConstant() {
        // private constructor to hide the implicit public one
        throw new AssertionError("PackageConstant class cannot be instantiated.");
    }

    /**
     * Maximum page number allowed.
     */
    public static final int MAX_PAGE_NUM = 100000;

    /**
     * Minimum page number allowed.
     */
    public static final int MIN_PAGE_NUM = 1;

    /**
     * Maximum page size allowed.
     */
    public static final int MAX_PAGE_SIZE = 100;

    /**
     * Minimum page size allowed.
     */
    public static final int MIN_PAGE_SIZE = 5;

    /**
     * Maximum field length allowed.
     */
    public static final int MAX_FIELD_LENGTH = 1000;

}
