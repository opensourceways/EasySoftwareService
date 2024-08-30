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

import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.JdkSslContext;
import io.netty.handler.ssl.SslProtocols;

import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Map;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.easysoftware.common.constant.HttpConstant;
import com.easysoftware.common.constant.MapConstant;
import com.easysoftware.common.constant.PackageConstant;
import com.easysoftware.infrastructure.applyform.gatewayimpl.dataobject.ApplyFormDO;

@Component
public final class EsAsyncHttpUtil {
    /**
     * Value injected for the es url.
     */
    @Value("${es.url}")
    private String esUrl;

    /**
     * Value injected for the es user.
     */
    @Value("${es.user}")
    private String esUser;

    /**
     * Value injected for the es password.
     */
    @Value("${es.pwd}")
    private String pwd;

    /**
     * Value injected for the es searchFormat.
     */
    @Value("${es.searchFormat}")
    private String searchFormat;

    /**
     * Value injected for the es searchRepoFormat.
     */
    @Value("${es.searchRepoFormat}")
    private String searchRepoFormat;

    /**
     * Value injected for the es updateFormat.
     */
    @Value("${es.updateFormat}")
    private String updateFormat;

    /**
     * AsyncHttpClient.
     */
    private static volatile AsyncHttpClient asyncHttpClient = null;

    /**
     * get es client.
     * @return AsyncHttpClient
     */
    public static synchronized AsyncHttpClient getClient() throws KeyManagementException, NoSuchAlgorithmException {
        if (asyncHttpClient == null) {
            asyncHttpClient = new DefaultAsyncHttpClient(new DefaultAsyncHttpClientConfig.Builder()
                    .setConnectTimeout(HttpConstant.ES_TIME_OUT)
                    .setRequestTimeout(HttpConstant.ES_TIME_OUT)
                    .setSslContext(new JdkSslContext(skipSsl(), true, ClientAuth.NONE))
                    .build());
        }

        return asyncHttpClient;
    }

    /**
     * get es builder.
     * @return RequestBuilder
     */
    public RequestBuilder getBuilder() {
        RequestBuilder builder = new RequestBuilder();
        builder.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        builder.addHeader(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64.getEncoder().encodeToString((esUser + ":" + pwd).getBytes(StandardCharsets.UTF_8)))
                .setMethod(HttpConstant.POST);
        return builder;

    }

    /**
     * execute es search.
     * @param index es index name
     * @param obj   search condition
     * @param login  gitee id of user
     * @return ListenableFuture<Response> the result of es search.
     */
    public ListenableFuture<Response> executeSearch(String index, Map<String, Object> obj, String login)
            throws NoSuchAlgorithmException, KeyManagementException {
        AsyncHttpClient client = getClient();
        RequestBuilder builder = getBuilder();
        String query = convertQuery(obj, login);

        builder.setUrl(esUrl + index + "/_search");
        builder.setBody(query);

        return client.executeRequest(builder.build());
    }

    /**
     * execute es search.
     *
     * @param index es index name
     * @param login  gitee id of user
     * @return ListenableFuture<Response> the result of es search.
     */
    public ListenableFuture<Response> executeSearch(String index, String login)
            throws NoSuchAlgorithmException, KeyManagementException {
        AsyncHttpClient client = getClient();
        RequestBuilder builder = getBuilder();
        String query = String.format(searchRepoFormat, login);

        builder.setUrl(esUrl + index + "/_search");
        builder.setBody(query);

        return client.executeRequest(builder.build());
    }

    /**
     * execute es update by query.
     *
     * @param index  es index name
     * @param applyFormDO apply content
     * @param status package status
     * @return ListenableFuture<Response> the result of es update.
     */
    public ListenableFuture<Response> executeUpdate(String index, ApplyFormDO applyFormDO, String status)
            throws NoSuchAlgorithmException, KeyManagementException {
        AsyncHttpClient client = getClient();
        RequestBuilder builder = getBuilder();
        String metric = MapConstant.METRIC_MAP.get(applyFormDO.getMetric());
        String field = metric.split(".status")[0];

        String query = String.format(updateFormat, metric, field, applyFormDO.getMetricStatus(), status, "*",
                applyFormDO.getRepo());

        builder.setUrl(esUrl + index + "/_update_by_query");
        builder.setBody(query);

        return client.executeRequest(builder.build());
    }

    /**
     * convert condition to query string.
     * @param obj search condition
     * @param login  gitee id of user
     * @return query string.
     */
    public String convertQuery(Map<String, Object> obj, String login) {
        String queryString = "";
        for (Map.Entry<String, Object> entry : obj.entrySet()) {
            String field = entry.getKey();
            if (field.contains("page")) {
                continue;
            }
            String value = entry.getValue().toString();
            if (value.isBlank() || value.isEmpty()) {
                continue;
            }
            String valueString = getFilterList(value.split(PackageConstant.PARAMETER_SEP));
            queryString += String.format(PackageConstant.KEY_WORD_FORMAT, MapConstant.METRIC_MAP.get(field),
                    valueString);
        }
        String pageStr = obj.get("pageSize") == null ? "1" : obj.get("pageSize").toString();
        String fromStr = obj.get("pageNum") == null ? "10" : obj.get("pageNum").toString();
        int pageSize = Integer.parseInt(pageStr);
        int from = (Integer.parseInt(fromStr) - 1) * pageSize;
        String query = String.format(searchFormat, from, pageSize, login, queryString);
        return query;
    }

    /**
     * convert condition to query string.
     *
     * @param valueList query value list.
     * @return query string.
     */
    public String getFilterList(String[] valueList) {
        String result = "(";
        for (String value : valueList) {
            result += "\\\"" + value + "\\\",";
        }
        result += ")";
        return result;
    }

    /**
     * skip ssl.
     * @return SSLContext
     */
    public static SSLContext skipSsl() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance(SslProtocols.TLS_v1_2);

        X509TrustManager trustManager = new X509ExtendedTrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket)
                    throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket)
                    throws CertificateException {

            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine)
                    throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine)
                    throws CertificateException {

            }

            @Override
            public void checkClientTrusted(
                    X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        sc.init(null, new TrustManager[] {
                trustManager
        }, secureRandom);
        return sc;
    }
}
