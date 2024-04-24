package com.easysoftware.adapter.query;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.ReactiveRedisConnection.MultiValueResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageDetailVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageDetailVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.common.entity.ResultVo;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.common.utils.CommonUtil;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import com.easysoftware.domain.epkgpackage.gateway.EPKGPackageGateway;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.power.common.constants.BaseErrorCode.Common;

import jakarta.validation.constraints.AssertTrue;
import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class DomainPackageQueryAdapterTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // test /domain/stat
    @Test
    void test_domain_stat() throws Exception {
        ResultVo res = CommonUtil.executeGet(mockMvc, "/domain/stat", null);

        CommonUtil.assertOk(res);

        assertTrue(res.getData() instanceof Map);
        if (res.getData() instanceof Map) {
            Map<?, ?> data = (Map<?, ?>) res.getData();
            assertTrue(data.keySet().stream().allMatch(key -> key instanceof String));
            assertTrue(data.values().stream().allMatch(value -> value instanceof Integer));

            assertTrue(data.containsKey("total") && (Integer) data.get("total") >= 0);
            assertTrue(data.containsKey("apppkg") && (Integer) data.get("apppkg") >= 0);
        }
    }

    @Test
    void test_domain_column_exception() throws Exception {
        ResultVo res = performDomainColumn("error", "version");
        CommonUtil.assert400(res);
        res = performDomainColumn("rpmpkg", "error");
        CommonUtil.assert400(res);
        res = performDomainColumn("epkgpkg", "error");
        CommonUtil.assert400(res);
    }

    // test /domain/column
    @Test
    void test_domain_column() throws Exception {
        List<String> names = List.of("rpmpkg", "epkgpkg");
        List<String> columns = List.of("arch", "version", "os", "category", "category, os, arch");

        for (String name : names) {
            for (String column : columns) {
                ResultVo res = performDomainColumn(name, column);
                CommonUtil.assertOk(res);
            }
        }
    }

    private ResultVo performDomainColumn(String name, String column) throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("column", column);
        paramMap.add("name", name);
        ResultVo res = CommonUtil.executeGet(mockMvc, "/domain/column", paramMap);
        return res;
    }

    @Test
    void test_domain_detail_exception() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("appPkgId", "error");
        paramMap.add("epkgPkgId", "openEuler-22.03-LTS-SP1texlive-apnum-docsvn47510-24noarch");
        paramMap.add("rpmPkgId", "openeEuler-22.03-LTS-SP1texlive-apnum-docsvn47510-24noarch");
        ResultVo res = CommonUtil.executeGet(mockMvc, "/domain/detail", paramMap);
        CommonUtil.assert400(res);

        paramMap = new LinkedMultiValueMap<>();
        paramMap.add("appPkgId", "memcached");
        paramMap.add("epkgPkgId", "error");
        paramMap.add("rpmPkgId", "openeEuler-22.03-LTS-SP1texlive-apnum-docsvn47510-24noarch");
        res = CommonUtil.executeGet(mockMvc, "/domain/detail", paramMap);
        CommonUtil.assert400(res);

        paramMap = new LinkedMultiValueMap<>();
        paramMap.add("appPkgId", "memcached");
        paramMap.add("epkgPkgId", "openEuler-22.03-LTS-SP1texlive-apnum-docsvn47510-24noarch");
        paramMap.add("rpmPkgId", "error");
        res = CommonUtil.executeGet(mockMvc, "/domain/detail", paramMap);
        CommonUtil.assert400(res);
    }

    // test /domain/detail
    @Test
    void test_domain_detail() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("appPkgId", "memcached");
        paramMap.add("epkgPkgId", "openEuler-22.03-LTS-SP1texlive-apnum-docsvn47510-24noarch");
        paramMap.add("rpmPkgId", "openeEuler-22.03-LTS-SP1texlive-apnum-docsvn47510-24noarch");
        ResultVo res = CommonUtil.executeGet(mockMvc, "/domain/detail", paramMap);

        CommonUtil.assertOk(res);
        assertTrue(res.getData() instanceof Map);
        if (res.getData() instanceof Map) {
            Map<?, ?> data = (Map<?, ?>) res.getData();
            assertTrue(data.keySet().stream().allMatch(key -> key instanceof String));

            assertTrue(data.containsKey("IMAGE"));
            assertTrue(data.containsKey("EPKG"));
            assertTrue(data.containsKey("RPM"));
            assertTrue(data.containsKey("tags"));
        }
    }

    // test
    //     1. /domain?name=apppkg
    //     2. /domain?name=rpmpkg
    //     3. /domain?name=epkgpkg
    //     4. /domain?name=all
    @Test
    void test_domain_pkg() throws Exception {
        List<String> names = List.of("apppkg", "rpmpkg", "epkgpkg", "all");
        for (String name : names) {
            ResultVo res = performDomainPkg(name);
            CommonUtil.assertList(res);
        }
    }

    @Test
    void test_domain_pkg_exception() throws Exception {
        ResultVo res = performDomainPkg("error");
        CommonUtil.assert400(res);
    }

    private ResultVo performDomainPkg(String name) throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name", name);
        paramMap.add("timeOrder", "asc");
        ResultVo res = CommonUtil.executeGet(mockMvc, "/domain", paramMap);
        return res;
    }

    // test search rpmpkg with multiparam
    @Test
    void test_domain_multiparam() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name", "rpmpkg");
        paramMap.add("os", "openEuler-20.03-LTS-SP2");
        paramMap.add("arch", "noarch, x86_64");
        paramMap.add("timeOrder", "desc");
        ResultVo res = CommonUtil.executeGet(mockMvc, "/domain", paramMap);
        CommonUtil.assertList(res);
    }

    @Test
    void test_domain_multiparam_exception() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name", "error");
        paramMap.add("os", "openEuler-20.03-LTS-SP2");
        paramMap.add("arch", "noarch, x86_64");
        paramMap.add("timeOrder", "desc");
        ResultVo res = CommonUtil.executeGet(mockMvc, "/domain", paramMap);
        CommonUtil.assert400(res);

        paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name", "rpmpkg");
        paramMap.add("os", "error");
        paramMap.add("arch", "noarch, x86_64");
        paramMap.add("timeOrder", "desc");
        res = CommonUtil.executeGet(mockMvc, "/domain", paramMap);
        CommonUtil.assertNone(res);
    }
}
