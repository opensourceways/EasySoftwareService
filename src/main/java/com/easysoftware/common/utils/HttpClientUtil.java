package com.easysoftware.common.utils;

import com.easysoftware.common.constant.HttpConstant;
import com.easysoftware.common.entity.MessageCode;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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
     * Send a GET request to the specified URL and retrieve the response as a string.
     *
     * @param urlStr The URL to send the GET request to.
     * @return The response from the GET request as a string.
     */
    public static String getRequest(final String urlStr) {
        try {
            URL url = new URL(urlStr);

            if (!sercuritySSRFUrlCheck(url)) {
                throw new IllegalArgumentException("URL is vulnerable to SSRF attacks");
            }

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HttpConstant.GET);
            connection.setConnectTimeout(HttpConstant.TIME_OUT); // 设置连接超时，单位毫秒
            connection.setReadTimeout(HttpConstant.TIME_OUT); // 设置读取超时，单位毫秒
            int responseCode = connection.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),
                    StandardCharsets.UTF_8))) {
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
        } catch (Exception e) {
            LOGGER.error(MessageCode.EC0001.getMsgEn(), e);
        }
        return null;
    }

    /**
     * Send a POST request to the specified URL with a given request body and retrieve the response as a string.
     *
     * @param urlStr The URL to send the POST request to.
     * @param body   The request body for the POST request.
     * @return The response from the POST request as a string.
     */
    public static String postRequest(final String urlStr, final String body) {
        try {
            URL url = new URL(urlStr);

            if (!sercuritySSRFUrlCheck(url)) {
                throw new IllegalArgumentException("URL is vulnerable to SSRF attacks");
            }

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(HttpConstant.POST);
            connection.setConnectTimeout(HttpConstant.TIME_OUT);
            connection.setReadTimeout(HttpConstant.TIME_OUT);
            connection.setRequestProperty(HttpConstant.CONTENT_TYPE, "application/json");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }

            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(body.getBytes());
            }
            try (InputStream inputStream = connection.getInputStream();
                 InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                 BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
        } catch (Exception e) {
            LOGGER.error(MessageCode.EC0001.getMsgEn(), e);
        }
        return null;
    }

    /**
     * Get an HTTP client with specified parameters.
     *
     * @param uri       The URI for the HTTP client.
     * @param token     The token to include in the request.
     * @param userToken The user token to include in the request.
     * @param cookie    The cookie value to include in the request.
     * @return The HTTP client as a string.
     */
    public static String getHttpClient(final String uri, final String token, final String userToken, final String cookie) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setConfig(REQUEST_CONFIG);

        if (token != null) httpGet.addHeader(HttpConstant.TOKEN, token);
        if (userToken != null) httpGet.addHeader(HttpConstant.USER_TOKEN, userToken);
        if (cookie != null) httpGet.addHeader(HttpConstant.COOKIE, "_Y_G_=" + cookie);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.EC0001.getMsgEn());
        }
    }

    /**
     * Send a POST request using an HTTP client to the specified URI with the given request body.
     *
     * @param uri         The URI for the POST request.
     * @param requestBody The body of the POST request.
     * @return The response from the POST request as a string.
     */
    public static String postHttpClient(final String uri, final String requestBody) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setConfig(REQUEST_CONFIG);
        try {
            httpPost.setHeader(HttpConstant.CONTENT_TYPE, "application/json");
            StringEntity stringEntity = new StringEntity(requestBody);
            httpPost.setEntity(stringEntity);
            HttpResponse response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.EC0001.getMsgEn());
        }
    }

    /**
     * Perform a security check for SSRF on the provided URL.
     *
     * @param url The URL to check for SSRF.
     * @return Boolean value indicating the SSRF security check result.
     */
    // ssrf检查，whitelist todo
    private static Boolean sercuritySSRFUrlCheck(final URL url) {
        return url.getProtocol().startsWith("http") || url.getProtocol().startsWith("https");
    }
}
