package com.easysoftware.adapter.execute;

import static org.junit.jupiter.api.DynamicTest.stream;

import java.util.List;
import java.util.Map;

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
import com.easysoftware.application.epkgpackage.EPKGPackageService;
import com.easysoftware.application.externalos.dto.ExternalOsSearchCondiiton;
import com.easysoftware.application.externalos.dto.InputExternalOs;
import com.easysoftware.common.entity.ResultVo;
import com.easysoftware.common.utils.CommonUtil;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.power.common.constants.BaseErrorCode.Common;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class ExternalOsExecuteAdapterTest {
        private static final String REQUEST_MAPPING = "/externalos";
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
    public void test_insert() throws Exception {
        // CommonUtil.executeDelete(mockMvc, REQUEST_MAPPING + "/testfortest", null);
        
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

        // test update
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("originOsName", "test--originosname");
        res = CommonUtil.executeGet(mockMvc, REQUEST_MAPPING, paramMap);
        
        CommonUtil.assertList(res);
        List<Map<String, String>> list = CommonUtil.getList(res);
        String id = list.get(0).get("id");
        input = new InputExternalOs();
        input.setOriginOsName("test--update--oriignosname");
        input.setId(id);
        res = CommonUtil.executePut(mockMvc, REQUEST_MAPPING, ObjectMapperUtil.writeValueAsString(input));
        CommonUtil.assertOk(res);

        // test delete
        res = CommonUtil.executeDelete(mockMvc, REQUEST_MAPPING + "/" + id, null);
        CommonUtil.assertOk(res);
    }


}
