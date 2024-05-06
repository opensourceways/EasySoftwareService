package com.easysoftware.common.utils;

import com.easysoftware.common.constant.MapConstant;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Map;

public final class ApiUtil {

    // Private constructor to prevent instantiation of the utility class
    private ApiUtil() {
        // private constructor to hide the implicit public one
        throw new AssertionError("ApiUtil class cannot be instantiated.");
    }

    /**
     * Fetches and returns an API response map based on the provided URL.
     *
     * @param url The URL to fetch the API response from
     * @return A map containing the API response data
     */
    public static Map<String, String> getApiResponseMap(final String url) {
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

    /**
     * Fetches and returns an API response map for a maintainer based on the provided URL.
     *
     * @param url The URL to fetch the API response from
     * @return A map containing the maintainer's API response data
     */
    public static Map<String, String> getApiResponseMaintainer(final String url) {
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

    /**
     * Fetches and returns the API response data based on the provided URL.
     *
     * @param url The URL to fetch the API response from
     * @return The API response data as a string
     */
    public static String getApiResponseData(final String url) {
        String response = HttpClientUtil.getHttpClient(url, null, null, null);
        JsonNode info = ObjectMapperUtil.toJsonNode(response);
        if (info.get("code").asInt() == 200 && !info.get("data").isNull()) {
            return info.get("data").asText();
        }
        return null;
    }

}
