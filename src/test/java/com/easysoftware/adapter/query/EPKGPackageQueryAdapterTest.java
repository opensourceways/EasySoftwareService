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

import com.easysoftware.common.entity.ResultVo;
import com.easysoftware.common.utils.CommonUtil;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class EPKGPackageQueryAdapterTest {
        @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @MockBean
    private RPMPackageGateway gateway;
 
    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // test /epkgpkg?
    @Test
    void test_epkg() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name", "grafana");
        paramMap.add("os", "openEuler-20.03-LTS-SP1");
        ResultVo res = CommonUtil.executeGet(mockMvc, "/epkgpkg", paramMap);
        CommonUtil.assertList(res);
    }

    @Test
    void test_epkg_exception() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name", "error");
        paramMap.add("os", "openEuler-20.03-LTS-SP1");
        ResultVo res = CommonUtil.executeGet(mockMvc, "/epkgpkg", paramMap);
        CommonUtil.assertNone(res);
    }
}
