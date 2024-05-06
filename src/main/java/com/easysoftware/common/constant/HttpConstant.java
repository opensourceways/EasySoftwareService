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

}
