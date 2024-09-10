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

import static org.mockito.Mockito.when;

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

import com.easysoftware.common.account.UserPermission;
import com.easysoftware.common.entity.ResultVo;
import com.easysoftware.common.utils.CommonUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class CoMaintainerAdapterTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserPermission userPermission;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testQueryRepos() throws Exception {
        performMaintainerQueryRepos();
    }

    private void performMaintainerQueryRepos() throws Exception {
        when(userPermission.getUserLogin()).thenReturn("user");
        when(userPermission.checkUserMaintainerPermission()).thenReturn(true);
        
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("pageNum", "1");
        paramMap.add("pageSize", "10");
        paramMap.add("repo", "jieba,libsvm");
        paramMap.add("sigName", "ai");
        paramMap.add("status", "活跃");
        paramMap.add("versionStatus", "落后版本");
        paramMap.add("cveStatus", "没有CVE问题");
        ResultVo res = CommonUtil.executeGet(mockMvc, "/collaboration/maintainer/user/repos", paramMap);
        CommonUtil.assertList(res);
    }
}
