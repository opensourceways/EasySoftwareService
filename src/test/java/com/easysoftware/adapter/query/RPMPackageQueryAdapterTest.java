package com.easysoftware.adapter.query;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easysoftware.application.rpmpackage.RPMPackageService;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.common.constant.PackageConstant;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;
import com.easysoftware.infrastructure.mapper.RPMPackageDOMapper;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.RPMPackageGatewayImpl;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class RPMPackageQueryAdapterTest {
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
    public void test_rpmpkg_search_condition() throws Exception {
        String pkgId = "openEuler-20.03-LTS-SP1debuginfoaarch64aalib-debuginfo1.4.0-1.oe1aarch64";
        List<RPMPackageDetailVo> list = createMockListPkg();
        when(gateway.queryDetailByPkgId(pkgId)).thenReturn(list);

        String content = performAndReturn();
        
        boolean right = rightContent(content, list);
        assertTrue(right);
    }

    private boolean rightContent(String content, List<RPMPackageDetailVo> rpmList) throws Exception {
        Map<String, Object> rpmMap = extractContent(content);
        RPMPackageDetailVo rpmPkg = rpmList.get(0);
        return compare(rpmMap, rpmPkg);
    }

    private boolean compare(Map<String, Object> rpmMap, RPMPackageDetailVo rpmPkg) throws Exception {
        Field[] fields = RPMPackageDetailVo.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String filedName = field.getName();
            Object value = field.get(rpmPkg);
            Object mapValue = rpmMap.get(filedName);

            if (value == null && mapValue == null) {
                continue;
            } else if ((value == null && mapValue != null) || (value != null && mapValue == null)) {
                return false;
            } else if (value != null && mapValue != null) {
                String valueS = (String) value;
                String mapValueS = (String) mapValue;
                if (! valueS.equals(mapValueS)) {
                    return false;
                }
            } else {
            }
        }
        return true;
    }

    private Map<String, Object> extractContent(String content) {
        Map<String, Object> map = ObjectMapperUtil.toMap(content);
        Map<String, Object> data = (Map<String, Object>) map.get("data");
        List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("list");
        Map<String, Object> rpmMap = list.get(0);
        return rpmMap;
    }

    private String performAndReturn() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/rpmpkg")
                .param("name", "aalib-debuginfo")
                .param("version", "1.4.0-1.oe1")
                .param("arch", "aarch64")
                .param("os", "openEuler-20.03-LTS-SP1")
                .param("subPath", "debuginfoaarch64")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        return content;
    }

    private List<RPMPackageDetailVo> createMockListPkg() {
        RPMPackageDetailVo rpm = new RPMPackageDetailVo();
        rpm.setName("aalib-debuginfo");
        rpm.setVersion("1.4.0-1.oe1");
        rpm.setOs("openEuler-20.03-LTS-SP1");
        rpm.setArch("aarch64");
        rpm.setCategory("其他");
        rpm.setBinDownloadUrl("https://repo.openeuler.org/openEuler-20.03-LTS-SP1/debuginfo/aarch64/Packages/aalib-debuginfo-1.4.0-1.oe1.aarch64.rpm");
        rpm.setSubPath("debuginfoaarch64");
        
        List<RPMPackageDetailVo> list = new ArrayList<>();
        list.add(rpm);
        return list;
    }
}
