package com.easysoftware.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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

import com.easysoftware.common.constant.HttpConstant;
import com.easysoftware.common.entity.MessageCode;

public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static final RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(HttpConstant.TIME_OUT)
            .setSocketTimeout(HttpConstant.TIME_OUT).build();

    public static String getRequest(String urlStr) {
        try {
            URL url = new URL(urlStr);
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
            logger.error(MessageCode.EC0001.getMsgEn(), e);
        }
        return null;
    }

    public static String postRequest(String urlStr, String body) {
        try {
            URL url = new URL(urlStr);
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
            logger.error(MessageCode.EC0001.getMsgEn(), e);
        }
        return null;
    }

    public static String getHttpClient(String uri, String token, String userToken, String cookie) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setConfig(requestConfig);

        if (token != null) httpGet.addHeader(HttpConstant.TOKEN, token);
        if (userToken != null) httpGet.addHeader(HttpConstant.USER_TOKEN, userToken);
        if (cookie != null) httpGet.addHeader(HttpConstant.COOKIE, "_Y_G_=" + cookie);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            String responseBody = EntityUtils.toString(response.getEntity());
            return responseBody;
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.EC0001.getMsgEn());
        }
    }

    public static String postHttpClient(String uri, String requestBody) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setConfig(requestConfig);
        try {
            httpPost.setHeader(HttpConstant.CONTENT_TYPE, "application/json");
            StringEntity stringEntity = new StringEntity(requestBody);
            httpPost.setEntity(stringEntity);
            HttpResponse response = httpClient.execute(httpPost);
            String responseBody = EntityUtils.toString(response.getEntity());
            return responseBody;
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.EC0001.getMsgEn());
        }
    }
}
