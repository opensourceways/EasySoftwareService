package com.easysoftware.adapter.execute;


import static org.junit.Assert.assertEquals;

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

import com.easysoftware.application.epkgpackage.dto.InputEPKGPackage;
import com.easysoftware.common.entity.ResultVo;
import com.easysoftware.common.utils.CommonUtil;
import com.easysoftware.common.utils.ObjectMapperUtil;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class EPKGPackageExecuteAdapterTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test_update() throws Exception {
        // 将某数据的arch更新为`testarch`
        InputEPKGPackage input = new InputEPKGPackage();
        input.setArch("testarch");
        input.setPkgId("openEuler-22.03-LTS-SP1texlive-apnum-docsvn47510-24noarch");
        ResultVo res = CommonUtil.executePut(mockMvc, "/epkgpkg", ObjectMapperUtil.writeValueAsString(input));
        CommonUtil.assertOk(res);
        
        // 查询更新是否成功
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("pkgId", "openEuler-22.03-LTS-SP1texlive-apnum-docsvn47510-24noarch");
        res = CommonUtil.executeGet(mockMvc, "/epkgpkg", paramMap);
        CommonUtil.assertList(res);
        List<Map<String, String>> list = CommonUtil.getList(res);
        assertEquals(list.get(0).get("arch"), "testarch");
    }

    @Test
    public void test_delete() throws Exception {
        // 删除数据
        String url = "/epkgpkg/openEuler-22.03-LTS-SP1texlive-apnum-docsvn47510-24noarch";
        ResultVo res = CommonUtil.executeDelete(mockMvc, url, null);
        CommonUtil.assertOk(res);

        // 查询是否成功删除数据
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("pkgId", "openEuler-22.03-LTS-SP1texlive-apnum-docsvn47510-24noarch");
        res = CommonUtil.executeGet(mockMvc, "/epkgpkg", paramMap);
        CommonUtil.assertNone(res);
    }

    // @Test
    // public void test_

}
