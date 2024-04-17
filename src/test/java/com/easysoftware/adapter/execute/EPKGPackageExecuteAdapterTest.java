package com.easysoftware.adapter.execute;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.easysoftware.application.applicationpackage.dto.InputApplicationPackage;
import com.easysoftware.application.epkgpackage.EPKGPackageService;
import com.easysoftware.application.epkgpackage.dto.InputEPKGPackage;
import com.easysoftware.common.entity.ResultVo;
import com.easysoftware.common.utils.CommonUtil;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.domain.epkgpackage.EPKGPackage;
import com.easysoftware.domain.epkgpackage.gateway.EPKGPackageGateway;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.dataobject.EPKGPackageDO;
import com.easysoftware.infrastructure.mapper.EPKGPackageDOMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class EPKGPackageExecuteAdapterTest {
    private static final String REQUEST_MAPPING = "/epkgpkg";
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
        CommonUtil.executeDelete(mockMvc, REQUEST_MAPPING + "/testfortest", null);
        
        // test insert
        InputEPKGPackage input = new InputEPKGPackage();
        input.setPkgId("testfortest");
        input.setName("testfortest");
        String body = ObjectMapperUtil.writeValueAsString(input);
        ArrayList<String> list = new ArrayList<>();
        list.add(body);
        service.saveDataObjectBatch(list);
        
        // test update
        input.setArch("testarch");
        ResultVo res = CommonUtil.executePut(mockMvc, REQUEST_MAPPING, ObjectMapperUtil.writeValueAsString(input));
        CommonUtil.assertOk(res);

        // test delete
        res = CommonUtil.executeDelete(mockMvc, REQUEST_MAPPING + "/testfortest", null);
        CommonUtil.assertOk(res);
    }

    @Test
    public void test_insert_exception() throws Exception {
        CommonUtil.executeDelete(mockMvc, REQUEST_MAPPING + "/testfortest", null);
        
        // test insert
        InputEPKGPackage input = new InputEPKGPackage();
        input.setPkgId("testfortest");
        input.setName("testfortest");
        String body = ObjectMapperUtil.writeValueAsString(input);
        ArrayList<String> list = new ArrayList<>();
        list.add(body);
        service.saveDataObjectBatch(list);

        // 重复写入
        input = new InputEPKGPackage();
        input.setPkgId("testfortest");
        input.setName("testfortest");
        body = ObjectMapperUtil.writeValueAsString(input);
        list = new ArrayList<>();
        list.add(body);

        final ArrayList<String> fList = list;
        assertThrows(DuplicateKeyException.class, () -> {
            service.saveDataObjectBatch(fList);
        });
    }
}
