package com.easysoftware.adapter.query;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

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
@SpringBootTest
@AutoConfigureMockMvc
public class FieldPackageQueryAdapterTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void test_rpm() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("os", "os=openEuler-24.03-LTS");
        ResultVo res = CommonUtil.executeGet(mockMvc, "/field/archnum", paramMap);
        System.out.println(res);
        CommonUtil.assertOk(res);
    }

    @Test
    void test_column() throws Exception {
        List<String> names = List.of("rpmpkg", "epkgpkg", "apppkg", "domain", "oepkg");
        // List<String> names = List.of("oepkg");
        for (String name : names) {
            MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
            paramMap.put("name", List.of(name));
            paramMap.put("column", List.of("arch", "category", "os"));
            assertColumn(paramMap);
        }
    }

    void assertColumn(MultiValueMap<String, String> paramMap) throws Exception {
        ResultVo res = CommonUtil.executeGet(mockMvc, "/field/column", paramMap);
        CommonUtil.assertOk(res);
        Map<String, Object> data = (Map<String, Object>) res.getData();
        List<String> os = (List<String>) data.get("os");
        List<String> arch = (List<String>) data.get("arch");
        List<String> category = (List<String>) data.get("category");

        List<String> expectedOs = List.of("openEuler-24.03-LTS", "openEuler-23.09", "openEuler-23.03",
        "openEuler-22.09", "openEuler-22.03-LTS-SP4", "openEuler-22.03-LTS-SP3", "openEuler-22.03-LTS-SP2",
        "openEuler-22.03-LTS-SP1", "openEuler-22.03-LTS", "openEuler-21.09", "openEuler-21.03", "openEuler-20.09",
        "openEuler-20.03-LTS-SP4", "openEuler-20.03-LTS-SP3", "openEuler-20.03-LTS-SP2",
        "openEuler-20.03-LTS-SP1", "openEuler-20.03-LTS", "openEuler-preview");
        asertListOrder(os, expectedOs);

        List<String> expectedArch = List.of("aarch64", "i686", "loongarch64", "noarch", "riscv64","s390x", "sw_64","x86_64");
        asertListOrder(arch, expectedArch);
        List<String> expectedCategory = List.of("AI", "HPC", "云服务", "分布式存储", "大数据", "数据库",
                "其他");
        asertListOrder(category, expectedCategory);
    }

    void asertListOrder(List<String> actualList, List<String> expectedList) {
        int[] orders = new int[actualList.size()];
        for (int i = 0; i < actualList.size(); i++) {
            orders[i] = expectedList.indexOf(actualList.get(i));
        }
        for (int i = 1; i < orders.length; i++) {
            assertTrue(orders[i] > orders[i - 1]);
        }
    }
}
