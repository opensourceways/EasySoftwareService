package com.easysoftware.adapter.execute;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.easysoftware.application.epkgpackage.EPKGPackageService;
import com.easysoftware.application.operationconfig.dto.InputOperationConfig;
import com.easysoftware.common.entity.ResultVo;
import com.easysoftware.common.utils.CommonUtil;
import com.easysoftware.common.utils.ObjectMapperUtil;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class OperationConfigExecuteAdapterTest {
    private static final String REQUEST_MAPPING = "/operationconfig";
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private EPKGPackageService service;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void test_insert() throws Exception {
        CommonUtil.executeDelete(mockMvc, REQUEST_MAPPING + "/domainPage", null);
        InputOperationConfig input = new InputOperationConfig();
        input.setCategorys("大数据");
        input.setOrderIndex("1");
        input.setRecommend("kafka, redis");
        input.setType("domainPage");
        ResultVo res = CommonUtil.executePost(mockMvc, REQUEST_MAPPING, ObjectMapperUtil.writeValueAsString(input));
        CommonUtil.assertOk(res);

        res = CommonUtil.executeDelete(mockMvc, REQUEST_MAPPING + "/domainPage", null);
        CommonUtil.assertOk(res);
    }
}
