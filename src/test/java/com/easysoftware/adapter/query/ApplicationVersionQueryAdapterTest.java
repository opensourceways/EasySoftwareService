package com.easysoftware.adapter.query;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.easysoftware.application.applicationversion.ApplicationVersionService;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.entity.ResultVo;
import com.easysoftware.common.utils.CommonUtil;
import com.easysoftware.common.utils.ObjectMapperUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationVersionQueryAdapterTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ApplicationVersionService applicationVersionService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testAppVersion() throws Exception {
        // test case1: /appVersion?name=loki
        performAppVersion("loki");

        // test case2: /appVersion
        performAppVersion(null);
    }
    
    private void performAppVersion(String name) throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("pageNum", "1");
        paramMap.add("pageSize", "10");
        if (name != null) {
            paramMap.add("name", name);
        }
        ResultVo res = CommonUtil.executeGet(mockMvc, "/appVersion", paramMap);
        CommonUtil.assertList(res);
    }
}
