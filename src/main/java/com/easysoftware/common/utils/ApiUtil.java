package com.easysoftware.common.utils;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class ApiUtil {
    
    public static Map<String, String> getApiResponse(String url) {
        Map<String, String> res = new HashMap<>();
        String response = HttpClientUtil.getHttpClient(url);
        if (response != null) {
            JsonNode info = ObjectMapperUtil.toJsonNode(response);
            if (info.get("code").asInt() == 200 && !info.get("data").isNull()) {
                JsonNode infoData = info.get("data");
                res = ObjectMapperUtil.jsonToMap(infoData);
            }
        }
        return res;
    }
    
}
