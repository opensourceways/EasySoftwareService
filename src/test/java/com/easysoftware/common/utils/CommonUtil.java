package com.easysoftware.common.utils;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;

import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.entity.ResultVo;

public class CommonUtil {
    public static ResultVo executeGet(MockMvc mockMvc, String url, MultiValueMap<String, String> paramMap) throws Exception {
        String content = "";
        if (null == paramMap) {
            content = mockMvc.perform(MockMvcRequestBuilders.get(url)
                    .accept(MediaType.APPLICATION_JSON))
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        } else {
            content = mockMvc.perform(MockMvcRequestBuilders.get(url)
                    .params(paramMap)
                    .accept(MediaType.APPLICATION_JSON))
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        }
        ResultVo res = ObjectMapperUtil.toObject(ResultVo.class, content);
        return res;
    }

    public static ResultVo executePost(MockMvc mockMvc, String url, String jsonRequest) throws Exception {
        String content = mockMvc.perform(MockMvcRequestBuilders.post(url)
            .content(jsonRequest)
            .contentType(MediaType.APPLICATION_JSON))
            .andReturn().getResponse()
            .getContentAsString(StandardCharsets.UTF_8);

        ResultVo res = ObjectMapperUtil.toObject(ResultVo.class, content);
        return res;
    }

    public static ResultVo executePut(MockMvc mockMvc, String url, String jsonRequest) throws Exception {
        String content = mockMvc.perform(MockMvcRequestBuilders.put(url)
            .content(jsonRequest)
            .contentType(MediaType.APPLICATION_JSON))
            .andReturn().getResponse()
            .getContentAsString(StandardCharsets.UTF_8);

        ResultVo res = ObjectMapperUtil.toObject(ResultVo.class, content);
        return res;
    }

    public static ResultVo executeDelete(MockMvc mockMvc, String url) throws Exception {
        String content = mockMvc.perform(MockMvcRequestBuilders.delete(url)
            .contentType(MediaType.APPLICATION_JSON))
            .andReturn().getResponse()
            .getContentAsString(StandardCharsets.UTF_8);

        ResultVo res = ObjectMapperUtil.toObject(ResultVo.class, content);
        return res;
    }

    public static ResultVo executeDelete(MockMvc mockMvc, String url, MultiValueMap<String, String> paramMap) 
            throws Exception {
        String content = "";
        if (null == paramMap) {
            content = mockMvc.perform(MockMvcRequestBuilders.delete(url)
                    .accept(MediaType.APPLICATION_JSON))
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        } else {
            content = mockMvc.perform(MockMvcRequestBuilders.delete(url)
                    .params(paramMap)
                    .accept(MediaType.APPLICATION_JSON))
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        }
        ResultVo res = ObjectMapperUtil.toObject(ResultVo.class, content);
        return res;
    }

    public static void assertOk(ResultVo res) {
        assertEquals(res.getCode(), HttpStatus.OK.value());
        assertEquals(res.getMsg(), HttpStatus.OK.getReasonPhrase());
        assertNull(res.getError());
    }

    public static void assertList(ResultVo res) {
        assertTrue(res.getData() instanceof Map);
        if (res.getData() instanceof Map) {
            Map<?, ?> data = (Map<?, ?>) res.getData();
            if (data.size()> 0) {
                assertTrue(data.keySet().stream().allMatch(key -> key instanceof String));
                assertTrue(data.containsKey("total"));
                assertTrue(data.containsKey("list"));
            }
        }
    }

    public static void assertMsg(ResultVo res, MessageCode msg) {
        if (res.getMsg() instanceof Map) {
            Map<?, ?> msgVo = (Map<?, ?>) res.getMsg();
            if (msgVo.size()> 0) {
                assertEquals(msgVo.get("code"), msg.getCode());
            }
        }
    }

	public static void assertNone(ResultVo res) {
        assertList(res);
        Map<String, Object> data = (Map<String, Object>) res.getData();
        Integer total = (Integer) data.get("total");
        assertEquals(total, 0);
    }

    public static List<Map<String, String>> getList(ResultVo res) throws Exception {
        Map<String, Object> data = (Map<String, Object>) res.getData();
        List<Map<String, String>> list = (List<Map<String, String>>) data.get("list");
        return list;
    }
}
