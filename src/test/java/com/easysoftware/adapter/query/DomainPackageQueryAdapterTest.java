package com.easysoftware.adapter.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

    @Autowired
    private ObjectMapper objectMapper;
 
    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void test_domain_column() throws Exception {
        List<String> names = List.of("rpmpkg", "epkgpkg");
        List<String> columns = List.of("arch", "version", "os", "category");

        for (String name : names) {
            for (String column : columns) {
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/domain/column")
                .param("name", name)
                .param("column", column)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        
                String content = result.getResponse().getContentAsString(Charset.defaultCharset());
                Map<String, Object> res = objectMapper.readValue(content, Map.class);
                int code = (int) res.get("code");
                assertEquals(code, 200);
                
                log.debug("name: {}, column: {}, data: {}", name, column, res);
            }
        }

        
    }
}
