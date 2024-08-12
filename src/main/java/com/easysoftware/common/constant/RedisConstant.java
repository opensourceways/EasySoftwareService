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

public final class RedisConstant {

    // Private constructor to prevent instantiation of the RedisConstant class
    private RedisConstant() {
        // private constructor to hide the implicit public one
        throw new AssertionError("RedisConstant class cannot be instantiated.");
    }

    /**
     * Key of distinct application packages.
     */
    public static final String DISTINCT_OPEKGNUM = "distinct_opekg_num";
}
