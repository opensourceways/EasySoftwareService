package com.easysoftware.common.utils;

import java.util.HashMap;
import java.util.Map;

import com.easysoftware.common.constant.MapConstant;
import com.fasterxml.jackson.databind.JsonNode;

public class ApiUtil {

    public static Map<String, String> getApiResponseMap(String url) {
        Map<String, String> res = new HashMap<>();
        String response = HttpClientUtil.getHttpClient(url, null, null, null);
        if (response != null) {
            JsonNode info = ObjectMapperUtil.toJsonNode(response);
            if (info.get("code").asInt() == 200 && !info.get("data").isNull()) {
                JsonNode infoData = info.get("data");
                res = ObjectMapperUtil.jsonToMap(infoData);
            }
        }
        return res;
    }

    public static Map<String, String> getApiResponseMaintainer(String url) {
        //创建一个新的可修改的 Map，并将不可修改的 Map 中的所有元素复制到其中
        Map<String, String> maintainer = new HashMap<>(MapConstant.MAINTAINER);
        String response = HttpClientUtil.getHttpClient(url, null, null, null);
        if (response != null) {
            JsonNode info = ObjectMapperUtil.toJsonNode(response);
            if (info.get("code").asInt() == 200 && !info.get("data").isNull()) {
                JsonNode infoData = info.get("data");
                maintainer = ObjectMapperUtil.jsonToMap(infoData);
                maintainer.put(MapConstant.MAINTAINER_ID, maintainer.get(MapConstant.MAINTAINER_GITEE_ID));
            }
        }
        return maintainer;
    }

    public static String getApiResponseData(String url) {
        String response = HttpClientUtil.getHttpClient(url, null, null, null);
        JsonNode info = ObjectMapperUtil.toJsonNode(response);
        if (info.get("code").asInt() == 200 && !info.get("data").isNull()) {
            return info.get("data").asText();
        }
        return null;
    }

}
