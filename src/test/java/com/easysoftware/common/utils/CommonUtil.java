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

import static org.junit.Assert.assertNotNull;
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

    public static void assert400(ResultVo res) {
        assertEquals(res.getCode(), HttpStatus.BAD_REQUEST.value());
        assertNull(res.getData());
        assertNotNull(res.getMsg());
    }

    public static void assert500(ResultVo res) {
        assertEquals(res.getCode(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertNull(res.getData());
        assertNotNull(res.getError());
    }

    public static List<Map<String, String>> getList(ResultVo res) throws Exception {
        Map<String, Object> data = (Map<String, Object>) res.getData();
        List<Map<String, String>> list = (List<Map<String, String>>) data.get("list");
        return list;
    }
}
