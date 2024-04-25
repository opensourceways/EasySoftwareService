package com.easysoftware.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
            .setConnectTimeout(HttpConstant.timeOut)
            .setSocketTimeout(HttpConstant.timeOut).build();

    public static String getRequest(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // 设置连接超时，单位毫秒  
            connection.setReadTimeout(5000); // 设置读取超时，单位毫秒  
            int responseCode = connection.getResponseCode();  

            if (responseCode != HttpURLConnection.HTTP_OK) {  
                throw new IOException("HTTP error code: " + responseCode);  
            } 
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {  
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
            
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(HttpConstant.timeOut);
            connection.setReadTimeout(HttpConstant.timeOut);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }

            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(body.getBytes());
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
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

        if (token != null) httpGet.addHeader("token", token);
        if (userToken != null) httpGet.addHeader("user-token", userToken);
        if (cookie != null) httpGet.addHeader("Cookie", "_Y_G_=" + cookie);

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
            httpPost.setHeader("Content-Type", "application/json");
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
