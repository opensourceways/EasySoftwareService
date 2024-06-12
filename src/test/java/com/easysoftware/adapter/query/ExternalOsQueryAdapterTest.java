package com.easysoftware.adapter.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.easysoftware.application.externalos.dto.ExternalOsSearchCondiiton;
import com.easysoftware.application.externalos.dto.InputExternalOs;
import com.easysoftware.common.entity.ResultVo;
import com.easysoftware.common.utils.CommonUtil;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class ExternalOsQueryAdapterTest {
    private static final String REQUEST_MAPPING = "/externalos";

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @MockBean
    private RPMPackageGateway gateway;
 
    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void test_search() throws Exception {
        CommonUtil.executeDelete(mockMvc, REQUEST_MAPPING + "/testfortest", null);
        
        // test insert
        InputExternalOs input = new InputExternalOs();
        input.setOriginOsName("test--originosname");
        input.setOriginOsVer("test--originosver");
        input.setOriginPkg("test--originpkg");
        input.setTargetOsName("test--targetosname");
        input.setTargetOsVer("test--targetosver");
        input.setTargetPkg("test--targetpkg");
        ResultVo res = CommonUtil.executePost(mockMvc, REQUEST_MAPPING, ObjectMapperUtil.writeValueAsString(input));
        CommonUtil.assertOk(res);

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("originOsName", "test--originosname");
        res = CommonUtil.executeGet(mockMvc, REQUEST_MAPPING, paramMap);
        CommonUtil.assertOk(res);
    }
}