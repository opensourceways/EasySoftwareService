package com.easysoftware.adapter.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.easysoftware.application.applicationpackage.vo.ApplicationPackageDetailVo;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageDetailVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.common.utils.ParseResultUtil;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import com.easysoftware.domain.epkgpackage.gateway.EPKGPackageGateway;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.constraints.AssertTrue;
import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class DomainPackageQueryAdapterTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    private MockMvc mockMvc;

    @MockBean
    private RPMPackageGateway rpmGateway;

    @MockBean
    private EPKGPackageGateway epkgGateway;

    @MockBean
    private ApplicationPackageGateway appGateway;
 
    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // test /domain/stat
    @Test
    void test_domain_stat() throws Exception {
        long rpmTableLength = 100_0000L;
        long epkgTableLength = 2_0000L;
        long appTableLength = 100L;

        when(rpmGateway.queryTableLength()).thenReturn(rpmTableLength);
        when(epkgGateway.queryTableLength()).thenReturn(epkgTableLength);
        when(appGateway.queryTableLength()).thenReturn(appTableLength);

        String content = mockMvc.perform(MockMvcRequestBuilders.get("/domain/stat")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> map = (Map<String, Object>) ParseResultUtil.parseData(content);
        assertEquals(Long.valueOf((Integer) map.get("apppkg")), Long.valueOf(appTableLength));
        assertEquals(Long.valueOf((Integer) map.get("total")), Long.valueOf(rpmTableLength + epkgTableLength));
    }

    // test /domain/column
    @Test
    void test_domain_column() throws Exception {
        List<String> names = List.of("rpmpkg", "epkgpkg");
        List<String> columns = List.of("arch", "version", "os", "category");

        for (String name : names) {
            for (String column : columns) {
                boolean verified = performDomainColumn(name, column);
                assertTrue(verified);
            }
        }
    }

    private boolean performDomainColumn(String name, String column) throws Exception {
        List<String> mockList = getMockList(column);
        when(rpmGateway.queryColumn(column)).thenReturn(mockList);
        when(epkgGateway.queryColumn(column)).thenReturn(mockList);

        String content = performGet(name, column);
        List<String> list = (List<String>) ParseResultUtil.parseData(content);

        return verifyList(list, mockList);
    }

    private boolean verifyList(List<String> list1, List<String> list2) {
        Set<String> set1 = new HashSet<>(list1);
        Set<String> set2 = new HashSet<>(list2);
        return set1.equals(set2);
    }

    private String performGet(String name, String column) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/domain/column")
                .param("name", name)
                .param("column", column)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        return content;
    }

    private List<String> getMockList(String column) {
        if ("arch".equals(column)) {
            return List.of("aarch64", "noarch", "x86_64", "src", "loongarch64", "riscv64");
        } else if ("category".equals(column)) {
            return List.of("其他", "分布式存储", "云服务", "大数据", "AI", "HPC");
        } else if ("os".equals(column)) {
            return List.of("openEuler-20.09", "openEuler-21.03", "openEuler-22.03-LTS-SP1");
        } else if ("version".equals(column)) {
            return List.of("2.6.1-26.oe2003sp4", "3.3.1-5.oe2003sp4", "1.9.72-24.oe2003sp4");
        } else {
            throw new RuntimeException("error column");
        }
    }

    // test /domain/detail
    @Test
    void test_domain_detail() throws Exception {
        List<RPMPackageDetailVo> rpmList = mockRpmList();
        List<EPKGPackageDetailVo> epkgList = mockEpkgList();
        List<ApplicationPackageDetailVo> appList = mockAppList();
        Set<String> tags = Set.of("IMAGE", "RPM", "EPKG");

        when(rpmGateway.queryDetailByPkgId("openEuler-20.03-LTS-SP1everythingaarch64memcached1.5.10-5.oe1aarch64"))
                .thenReturn(rpmList);
        when(epkgGateway.queryDetailByPkgId("openEuler-22.03-LTS-SP1memcached1.6.12-2src"))
                .thenReturn(epkgList);
        when(appGateway.queryDetailByPkgId("memcached"))
                .thenReturn(appList);

        String content = mockMvc.perform(MockMvcRequestBuilders.get("/domain/detail")
                .param("appPkgId", "memcached")
                .param("epkgPkgId", "openEuler-22.03-LTS-SP1memcached1.6.12-2src")
                .param("rpmPkgId", "openEuler-20.03-LTS-SP1everythingaarch64memcached1.5.10-5.oe1aarch64")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        
        Map<String, Object> map = (Map<String, Object>) ParseResultUtil.parseData(content);
        boolean verified = verifyDetailMap(map, rpmList, epkgList, appList, tags);
        assertTrue(verified);
    }

    private boolean verifyDetailMap(Map<String, Object> map, List<RPMPackageDetailVo> rpmList, List<EPKGPackageDetailVo> 
            epkgList, List<ApplicationPackageDetailVo> appList, Set<String> tags) throws Exception {
        List<String> resTags = (List<String>) map.get("tags");
        if (resTags == null) {
            return false;
        }
        Set<String> set = new HashSet<>(resTags);
        if (! set.equals(tags)) {
            return false;
        }

        // compare rpm
        Map<String, String> rpmMap = (Map<String, String>) map.get("RPM");
        if (rpmMap == null) {
            return false;
        }
        RPMPackageDetailVo rpmObj = rpmList.get(0);
        if (! ParseResultUtil.compare(rpmMap, rpmObj)) {
            return false;
        }

        // compare image
        Map<String, String> imageMap = (Map<String, String>) map.get("IMAGE");
        if (imageMap == null) {
            return false;
        }
        ApplicationPackageDetailVo imageObj = appList.get(0);
        if (! ParseResultUtil.compare(imageMap, imageObj)) {
            return false;
        }

        // compare epkg
        Map<String, String> epkgMap = (Map<String, String>) map.get("EPKG");
        if (epkgMap == null) {
            return false;
        }
        EPKGPackageDetailVo epkgObj = epkgList.get(0);
        if (! ParseResultUtil.compare(epkgMap, epkgObj)) {
            return false;
        }

        return true;
    }

    private List<ApplicationPackageDetailVo> mockAppList() {
        ApplicationPackageDetailVo app = new ApplicationPackageDetailVo();
        app.setName("memcached");
        app.setCategory("数据库");
        List<ApplicationPackageDetailVo> list = new ArrayList<>();
        list.add(app);
        return list;
    }

    private List<EPKGPackageDetailVo> mockEpkgList() {
        EPKGPackageDetailVo epkg = new EPKGPackageDetailVo();
        epkg.setName("memcached");
        epkg.setOs("openEuler-20.03-LTS-SP1");
        epkg.setVersion("1.6.12-2");
        epkg.setArch("src");
        epkg.setEpkgSize("0.62MB");
        List<EPKGPackageDetailVo> list = new ArrayList<>();
        list.add(epkg);
        return list;
    }

    private List<RPMPackageDetailVo> mockRpmList() {
        RPMPackageDetailVo rpm = new RPMPackageDetailVo();
        rpm.setName("memcached");
        rpm.setOs("openEuler-20.03-LTS-SP1");
        rpm.setVersion("1.5.10-5.oe1");
        rpm.setArch("aarch64");
        rpm.setRpmSize("0.08MB");
        List<RPMPackageDetailVo> list = new ArrayList<>();
        list.add(rpm);
        return list;
    }
}

