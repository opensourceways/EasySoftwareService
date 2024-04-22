package com.easysoftware.adapter.common;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import org.apache.ibatis.exceptions.PersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.easysoftware.application.applicationpackage.ApplicationPackageService;
import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.entity.ResultVo;
import com.easysoftware.common.utils.CommonUtil;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class DatabaseExceptionTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    private MockMvc mockMvc;

    @MockBean
    private ApplicationPackageGateway gateway;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void test_mysql_exception() throws Exception {
        ApplicationPackageSearchCondition con = new ApplicationPackageSearchCondition();
        con.setName("apppkg");
        con.setPageNum(1);
        con.setPageSize(10);

        Exception e = new MyBatisSystemException(
                new PersistenceException(
                new CannotGetJdbcConnectionException("Failed to obtain JDBC Connection")));
        when(gateway.queryMenuByName(con)).thenThrow(e);

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name", "apppkg");
        ResultVo res = CommonUtil.executeGet(mockMvc, "/domain", paramMap);
        CommonUtil.assertMsg(res, MessageCode.ES0001);
    }
}
