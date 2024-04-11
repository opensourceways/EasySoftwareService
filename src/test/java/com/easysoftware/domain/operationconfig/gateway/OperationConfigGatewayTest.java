package com.easysoftware.domain.operationconfig.gateway;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.easysoftware.application.operationconfig.vo.OperationConfigVo;
import com.easysoftware.infrastructure.mapper.OperationConfigDOMapper;
import com.easysoftware.infrastructure.operationconfig.gatewayimpl.OperationConfigGatewayImpl;
import com.easysoftware.infrastructure.operationconfig.gatewayimpl.dataobject.OperationConfigDO;

@SpringBootTest
public class OperationConfigGatewayTest {
    @Autowired
    OperationConfigGateway gateway;

    @Autowired
    private OperationConfigDOMapper mapper;

    @Test
    void test_selectall() {
        List<OperationConfigVo> res = gateway.selectAll();
        assertTrue(res instanceof List);
    }
}
