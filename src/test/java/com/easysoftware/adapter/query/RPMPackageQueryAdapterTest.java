package com.easysoftware.adapter.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.charset.Charset;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class RPMPackageQueryAdapterTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
 
    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test_rpmpkg() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/rpmpkg")
        .param("name", "test")
        .accept(MediaType.APPLICATION_JSON))
        .andReturn();

        String content = result.getResponse().getContentAsString(Charset.defaultCharset());
        Map<String, Object> res = objectMapper.readValue(content, Map.class);
        int code = (int) res.get("code");
        assertEquals(code, 200);
    }
}
