/* Copyright (c) 2024 openEuler Community
 EasySoftware is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

package com.easysoftware.adapter.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.easysoftware.application.rpmpackage.RPMPackageService;
import com.easysoftware.application.rpmpackage.dto.RPMVersionCondition;
import com.easysoftware.application.rpmpackage.vo.PackgeVersionVo;
import com.easysoftware.common.entity.ResultVo;
import com.easysoftware.common.utils.CommonUtil;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class RPMPackageQueryAdapterTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Mock
    private RPMPackageGateway gateway;

    @Mock 
    private RPMPackageService rpmPackageSvc; 
    
    @InjectMocks
    private RPMPackageQueryAdapter rpmPackageQueryAdapter;
     
    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockitoAnnotations.openMocks(this); 
    }

    // test /rpmpkg?
    @Test
    void test_rpm() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name", "grafana");
        paramMap.add("os", "openEuler-20.03-LTS-SP1");
        ResultVo res = CommonUtil.executeGet(mockMvc, "/rpmpkg", paramMap);
        CommonUtil.assertList(res);
    }

    @Test
    void test_rpm_version() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name", "error");
        paramMap.add("os", "openEuler-20.03-LTS-SP1");
        ResultVo res = CommonUtil.executeGet(mockMvc, "/rpmpkg", paramMap);
        CommonUtil.assertList(res);
    }

    @Test
    void test_rpm_exception() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        // test case1 :
        paramMap.add("name", "test");
        ResultVo comres = CommonUtil.executeGet(mockMvc, "/rpmpkg/rpmver", paramMap);
        CommonUtil.assertList(comres);

        // test case2 :
        paramMap.add("name", "");
        ResultVo res = CommonUtil.executeGet(mockMvc, "/rpmpkg/rpmver", paramMap);
        CommonUtil.assertList(res);
    }

    @Test
    void test_VersionRequest() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        // test case1 :
        paramMap.add("type", "batchQuery");
        paramMap.add("os", "openEuler-24.03-LTS");
        ResultVo comres = CommonUtil.executeGet(mockMvc, "/rpmpkg/version", paramMap);
        CommonUtil.assertOk(comres);
    }

    @Test
    void test_VersionUpstreamRequest() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        // test case1 :
        paramMap.add("type", "batchQueryUpstream");
        ResultVo comres = CommonUtil.executeGet(mockMvc, "/rpmpkg/version", paramMap);
        CommonUtil.assertOk(comres);

         // test case1 :
         paramMap.add("type", "batchQueryUpstreame");
         ResultVo errores = CommonUtil.executeGet(mockMvc, "/rpmpkg/version", paramMap);
         Map<String, Object> data = (Map<String, Object>) errores.getData();
         assertEquals(data.get("total"), 0);
    }
    

    @Test
    void test_VersionWithErrorTypeException() throws Exception {
        RPMVersionCondition condition = new RPMVersionCondition(); 
        condition.setType("errorType");
        ResponseEntity<Object> mockVersion = null;  

        when(rpmPackageSvc.queryRpmVersion(condition)).thenReturn(mockVersion);  
  
        ResponseEntity<Object> response = rpmPackageQueryAdapter.queryRpmVersion(condition);  
  
        // 验证响应  
        assertEquals(response,null);  
        // 验证服务层方法是否被调用  
        verify(rpmPackageSvc, times(1)).queryRpmVersion(any(RPMVersionCondition.class));
    }

    @Test
    void test_VersionWithSuccess() throws Exception {
        RPMVersionCondition condition = new RPMVersionCondition(); 
        condition.setType("ee");

        Map<String, List<PackgeVersionVo>> gateWayReturn = new HashMap<>();
        PackgeVersionVo testVo = new PackgeVersionVo();
        testVo.setName("testRpm");
        gateWayReturn.computeIfAbsent("test", k -> new ArrayList<>()).add(testVo);
    
        ResponseEntity<Object> svcReturn = ResultUtil.success(HttpStatus.OK, Map.of(
            "total", gateWayReturn.size(),
            "res", gateWayReturn
        ));  

        ResponseEntity<Object> expReturn = ResultUtil.success(HttpStatus.OK, Map.of(
            "total", 1,
            "res", gateWayReturn
        ));  

        when(rpmPackageSvc.queryRpmVersion(condition)).thenReturn(svcReturn);  
        when(gateway.queryRpmVersionByOs(condition)).thenReturn(gateWayReturn);  

        ResponseEntity<Object> response = rpmPackageQueryAdapter.queryRpmVersion(condition);  
  
        // // 验证响应  
        assertEquals(expReturn,response);  
  
        // 验证服务层方法是否被调用  
        verify(rpmPackageSvc, times(1)).queryRpmVersion(any(RPMVersionCondition.class));
    }
}
