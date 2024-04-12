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
import com.easysoftware.application.applicationversion.dto.InputApplicationVersion;
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

    @Test
    void testInsertAppVersion() throws Exception {
        ArrayList<String> appList = new ArrayList<>();
        InputApplicationVersion app = new InputApplicationVersion();

        // test case1: not exist
        app.setName("redis--test--not--exist");
        app.setOpeneulerVersion("7.2.4");
        app.setUpstreamVersion("7.2.4");
        app.setStatus("OK");
        app.setCiVersion("7.2.4");
        app.setEulerHomepage("https://hub.docker.com/r/openeuler/redis");
        app.setUpHomepage("https://github.com/redis/redis");
        String jsonBody = ObjectMapperUtil.writeValueAsString(app);
        ResultVo res = CommonUtil.executePost(mockMvc, "/appVersion", jsonBody);
        CommonUtil.assertOk(res);
        String appStr = ObjectMapperUtil.writeValueAsString(app);
        appList.add(appStr);

        // test case2: exist
        app.setName("httpd");
        app.setOpeneulerVersion("2.4.58");
        app.setUpstreamVersion("2.4.58");
        app.setEulerHomepage("https://hub.docker.com/r/openeuler/httpd");
        app.setUpHomepage("https://github.com/apache/httpd");
        app.setStatus("OK");
        jsonBody = ObjectMapperUtil.writeValueAsString(app);
        res = CommonUtil.executePost(mockMvc, "/appVersion", jsonBody);
        CommonUtil.assertMsg(res, MessageCode.EC0008);
        appStr = ObjectMapperUtil.writeValueAsString(app);
        appList.add(appStr);

        applicationVersionService.saveDataObjectBatch(appList);
    }

    @Test
    void testUpdateAppVersion() throws Exception {
        InputApplicationVersion app = new InputApplicationVersion();

        // test case1: not exist
        app.setName("redis--test");
        app.setBackend("docker");
        app.setOpeneulerVersion("7.2.4");
        app.setUpstreamVersion("7.2.4");
        app.setStatus("OK");
        app.setCiVersion("7.2.4");
        app.setEulerHomepage("https://hub.docker.com/r/openeuler/redis");
        app.setUpHomepage("https://github.com/redis/redis");
        String jsonBody = ObjectMapperUtil.writeValueAsString(app);
        ResultVo res = CommonUtil.executePut(mockMvc, "/appVersion", jsonBody);
        CommonUtil.assertMsg(res, MessageCode.EC0009);
        
        // test case2: exist
        app.setName("loki");
        app.setOpeneulerVersion("2.9.6");
        app.setUpstreamVersion("2.9.6");
        app.setEulerHomepage("https://hub.docker.com/r/openeuler/loki");
        app.setUpHomepage("https://github.com/grafana/loki");
        app.setStatus("OK");
        jsonBody = ObjectMapperUtil.writeValueAsString(app);
        res = CommonUtil.executePut(mockMvc, "/appVersion", jsonBody);
        CommonUtil.assertOk(res);
    }

    @Test
    void testDeleteAppVersion() throws Exception {
        // test case1: not exist
        String url = "/appVersion/Prometheus-test-delete";
        ResultVo res = CommonUtil.executeDelete(mockMvc, url);

        // test case2: exist
        url = "/appVersion/Prometheus13";
        res = CommonUtil.executeDelete(mockMvc, url);
        CommonUtil.assertOk(res);
    }
}
