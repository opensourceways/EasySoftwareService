package com.easysoftware.adapter.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.easysoftware.common.entity.ResultVo;
import com.easysoftware.common.utils.CommonUtil;
import com.easysoftware.domain.oepackage.gateway.OEPackageGateway;

public class OEPackageQueryAdapterTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @MockBean
    private OEPackageGateway gateway;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // test /oepkg
    @Test
    void test_rpm() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name", "grafana");
        paramMap.add("os", "openEuler-20.03-LTS-SP1");
        ResultVo res = CommonUtil.executeGet(mockMvc, "/oepkg", paramMap);
        CommonUtil.assertList(res);
    }
}
