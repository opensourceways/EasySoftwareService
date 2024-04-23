package com.easysoftware.adapter.execute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.easysoftware.common.entity.ResultVo;
import com.easysoftware.common.utils.CommonUtil;
import com.easysoftware.redis.RedisService;


@SpringBootTest
@AutoConfigureMockMvc
public class RedisExecuteAdapterTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean  
    private RedisTemplate<String, Object> redisTemplate;  

    private MockMvc mockMvc;
    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void test_update() throws Exception {
    
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        // case 1 bad request 
        ResultVo res;
        paramMap.add("name", "domainPage"); 
        res = CommonUtil.executeGet(mockMvc, "/redis/update", paramMap);
        assertEquals(res.getCode(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        
        // case 2 bad request 
        paramMap.add("nameSpace", "domainPage"); 
        res = CommonUtil.executeGet(mockMvc, "/redis/update", paramMap);
        assertEquals(res.getCode(), HttpStatus.INTERNAL_SERVER_ERROR.value());

        // case 3 right request 
        paramMap.add("namespace", "domainPage"); 
        res = CommonUtil.executeGet(mockMvc, "/redis/update", paramMap);
        CommonUtil.assertOk(res);
    }
}