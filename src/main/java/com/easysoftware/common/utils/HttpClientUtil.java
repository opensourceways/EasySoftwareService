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

import java.io.IOException;
import java.net.URL;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easysoftware.common.constant.HttpConstant;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

public final class HttpClientUtil {
    // Private constructor to prevent instantiation of the utility class
    private HttpClientUtil() {
        // private constructor to hide the implicit public one
        throw new AssertionError("HttpClientUtil class cannot be instantiated.");
    }

    /**
     * Logger for HttpClientUtil.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * Request configuration with timeout settings.
     */
    private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom()
            .setConnectTimeout(HttpConstant.TIME_OUT)
            .setSocketTimeout(HttpConstant.TIME_OUT).build();

    /**
     * Get an HTTP client with specified parameters.
     *
     * @param uri       The URI for the HTTP client.
     * @param token     The token to include in the request.
     * @param userToken The user token to include in the request.
     * @param cookie    The cookie value to include in the request.
     * @return The HTTP client as a string.
     */
    public static String getHttpClient(final String uri, final String token,
            final String userToken, final String cookie) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setConfig(REQUEST_CONFIG);

        if (token != null) {
            httpGet.addHeader(HttpConstant.TOKEN, token);
        }
        if (userToken != null) {
            httpGet.addHeader(HttpConstant.USER_TOKEN, userToken);
        }
        if (cookie != null) {
            httpGet.addHeader(HttpConstant.COOKIE, "_Y_G_=" + cookie);
        }
        String responseRes = "";
        try {
            HttpResponse response = httpClient.execute(httpGet);
            responseRes = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            LOGGER.error("error happend in get request");
        }
        return responseRes;
    }

    /**
     * Send a POST request using an HTTP client to the specified URI with the given
     * request body.
     *
     * @param uri         The URI for the POST request.
     * @param requestBody The body of the POST request.
     * @return The response from the POST request as a string.
     */
    public static String postHttpClient(final String uri, final String requestBody) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setConfig(REQUEST_CONFIG);
        String responseRes = "";
        try {
            httpPost.setHeader(HttpConstant.CONTENT_TYPE, "application/json");
            StringEntity stringEntity = new StringEntity(requestBody);
            httpPost.setEntity(stringEntity);
            HttpResponse response = httpClient.execute(httpPost);
            responseRes = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            LOGGER.error("error happend in post request");
        }
        return responseRes;
    }

    /**
     * Perform a security check for SSRF on the provided URL.
     *
     * @param url The URL to check for SSRF.
     * @return Boolean value indicating the SSRF security check result.
     */
    // ssrf检查，whitelist todo
    private static Boolean sercuritySSRFUrlCheck(final URL url) {
        return url.getProtocol().startsWith(HttpConstant.HTTP_PROTOL)
                || url.getProtocol().startsWith(HttpConstant.HTTPS_PROTOL);
    }
}
