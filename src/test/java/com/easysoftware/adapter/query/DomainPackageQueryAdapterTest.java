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

    @Test
    void test_domain_search() throws Exception {
        List<String> names = List.of("epkgpkg", "rpmpkg");
        List<String> verisons = List.of("1.4.3.36-3");
        List<String> oses = List.of("openEuler-22.03");
        List<String> arches = List.of("aarch,");
        List<String> categories = List.of("Unspecified");
        List<String> tiemOrders = List.of("asc", "desc");

        for (String version : verisons) {
            for (String name : names) {
                for (String os : oses) {
                    for (String arch : arches) {
                        for (String category : categories) {
                            for (String timeOrder : tiemOrders) {
                                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/domain")
                                        .param("name", name)
                                        .param("os", os)
                                        .param("arch", arch)
                                        .param("version", version)
                                        .param("category", category)
                                        .param("timeOrder", timeOrder)
                                        .accept(MediaType.APPLICATION_JSON))
                                        .andReturn();
    
                                String content = result.getResponse().getContentAsString(Charset.defaultCharset());
                                Map<String, Object> res = objectMapper.readValue(content, Map.class);
                                int code = (int) res.get("code");
                                assertEquals(code, 200);
                                log.debug("name: {}, os: {}, arch: {}, category: {}, timeOrder: {}, res: {}", name, os, 
                                        arch, category, timeOrder, res);
                            }
                        }
                    }
                }
            }
        }
    }
}
