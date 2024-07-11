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

public final class HttpConstant {
    // Private constructor to prevent instantiation of the HttpConstant class
    private HttpConstant() {
        // private constructor to hide the implicit public one
        throw new AssertionError("HttpConstant class cannot be instantiated.");
    }

    /**
     * Timeout duration in milliseconds.
     */
    public static final int TIME_OUT = 5000;

    /**
     * HTTP GET method.
     */
    public static final String GET = "GET";

    /**
     * HTTP POST method.
     */
    public static final String POST = "POST";

    /**
     * Token key.
     */
    public static final String TOKEN = "token";

    /**
     * Cookie key.
     */
    public static final String COOKIE = "Cookie";

    /**
     * User token key.
     */
    public static final String USER_TOKEN = "user-token";

    /**
     * Content type key.
     */
    public static final String CONTENT_TYPE = "Content-Type";

    /**
     * http protol type key.
     */
    public static final String HTTP_PROTOL = "http";

    /**
     * https protol type key.
     */
    public static final String HTTPS_PROTOL = "https";
}
