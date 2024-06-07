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

    /**
     * VALID_STR_REG used to match input string.
     */
    public static final String VALID_STR_REG = "^$|^[\\u4E00-\\u9FA5A-Za-z0-9.()$\\-_+, ]+$";

    /**
     * VALID_MESSAGE, error message.
     */
    public static final String VALID_MESSAGE = "Null or string. String includes only letters, digits, and special "
            + "characters(_-+()$.,)";

    /**
     * HTTP_PREFIX, Referer pass prefix.
     */
    public static final String HTTP_PREFIX = "http://";

    /**
     * HTTPS_PREFIX, Referer pass prefix.
     */
    public static final String HTTPS_PREFIX = "https://";

}
