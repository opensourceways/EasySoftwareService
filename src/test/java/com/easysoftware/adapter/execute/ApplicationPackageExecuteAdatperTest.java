package com.easysoftware.adapter.execute;

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

import com.easysoftware.application.applicationpackage.dto.InputApplicationPackage;
import com.easysoftware.common.entity.ResultVo;
import com.easysoftware.common.utils.CommonUtil;
import com.easysoftware.common.utils.ObjectMapperUtil;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class ApplicationPackageExecuteAdatperTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test_insert() throws Exception {
        CommonUtil.executeDelete(mockMvc, "/apppkg/testfortest", null);
        
        // test insert
        InputApplicationPackage input = new InputApplicationPackage();
        input.setPkgId("testfortest");
        input.setName("testfortest");
        ResultVo res = CommonUtil.executePost(mockMvc, "/apppkg", ObjectMapperUtil.writeValueAsString(input));
        CommonUtil.assertOk(res);
        
        // test update
        input.setArch("tetarch");
        log.info("input: {}", ObjectMapperUtil.writeValueAsString(input));
        res = CommonUtil.executePut(mockMvc, "/apppkg", ObjectMapperUtil.writeValueAsString(input));
        CommonUtil.assertOk(res);

        // test delete
        res = CommonUtil.executeDelete(mockMvc, "/apppkg/testfortest", null);
        CommonUtil.assertOk(res);
    }


}
