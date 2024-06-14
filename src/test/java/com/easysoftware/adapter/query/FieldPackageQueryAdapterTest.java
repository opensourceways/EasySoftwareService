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

import static org.junit.jupiter.api.Assertions.assertTrue;
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

    // test /field/stat
    @Test
    void test_domain_stat() throws Exception {
        ResultVo res = CommonUtil.executeGet(mockMvc, "/field/stat", null);

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

    // test /field/column
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
        ResultVo res = CommonUtil.executeGet(mockMvc, "/field/column", paramMap);
        return res;
    }

    @Test
    void test_domain_detail_exception() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("appPkgId", "error");
        paramMap.add("epkgPkgId", "openEuler-22.03-LTS-SP1texlive-apnum-docsvn47510-24noarch");
        paramMap.add("rpmPkgId", "openeEuler-22.03-LTS-SP1texlive-apnum-docsvn47510-24noarch");
        ResultVo res = CommonUtil.executeGet(mockMvc, "/field/detail", paramMap);
        CommonUtil.assert400(res);

        paramMap = new LinkedMultiValueMap<>();
        paramMap.add("appPkgId", "memcached");
        paramMap.add("epkgPkgId", "error");
        paramMap.add("rpmPkgId", "openeEuler-22.03-LTS-SP1texlive-apnum-docsvn47510-24noarch");
        res = CommonUtil.executeGet(mockMvc, "/field/detail", paramMap);
        CommonUtil.assert400(res);

        paramMap = new LinkedMultiValueMap<>();
        paramMap.add("appPkgId", "memcached");
        paramMap.add("epkgPkgId", "openEuler-22.03-LTS-SP1texlive-apnum-docsvn47510-24noarch");
        paramMap.add("rpmPkgId", "error");
        res = CommonUtil.executeGet(mockMvc, "/field/detail", paramMap);
        CommonUtil.assert400(res);
    }

    // test /field/detail
    @Test
    void test_domain_detail() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("appPkgId", "memcached");
        paramMap.add("epkgPkgId", "openEuler-22.03-LTS-SP1texlive-apnum-docsvn47510-24noarch");
        paramMap.add("rpmPkgId", "openeEuler-22.03-LTS-SP1texlive-apnum-docsvn47510-24noarch");
        ResultVo res = CommonUtil.executeGet(mockMvc, "/field/detail", paramMap);

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
    //     1. /field?name=apppkg
    //     2. /field?name=rpmpkg
    //     3. /field?name=epkgpkg
    //     4. /field?name=all
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
        ResultVo res = CommonUtil.executeGet(mockMvc, "/field", paramMap);
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
        ResultVo res = CommonUtil.executeGet(mockMvc, "/field", paramMap);
        CommonUtil.assertList(res);
    }

    @Test
    void test_domain_multiparam_exception() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name", "error");
        paramMap.add("os", "openEuler-20.03-LTS-SP2");
        paramMap.add("arch", "noarch, x86_64");
        paramMap.add("timeOrder", "desc");
        ResultVo res = CommonUtil.executeGet(mockMvc, "/field", paramMap);
        CommonUtil.assert400(res);

        paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name", "rpmpkg");
        paramMap.add("os", "error");
        paramMap.add("arch", "noarch, x86_64");
        paramMap.add("timeOrder", "desc");
        res = CommonUtil.executeGet(mockMvc, "/field", paramMap);
        CommonUtil.assertNone(res);
    }
}
