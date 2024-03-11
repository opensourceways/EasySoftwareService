package com.easysoftware.adapter.execute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
import com.obs.services.internal.io.InterruptableInputStream;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RPMPackageExecuteAdapterTest {
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
    @Order(1)
    void test_insert_rpmpkg() throws Exception {
        String postJson = "{\n" +
                "\"name\": \"testtest\",\n" +
                "\"version\": \"test\"\n" +
                "}";

        System.out.println(postJson);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/rpmpkg")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postJson)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString(Charset.defaultCharset());
        Map<String, Object> res = objectMapper.readValue(content, Map.class);
        int code = (int) res.get("code");
        assertEquals(code, 200);
    }

    Map<String, String> searchPkg(String name) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/rpmpkg")
                .param("name", name)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString(Charset.defaultCharset());
        Map<String, Object> res = objectMapper.readValue(content, Map.class);
        
        Map<String, Object> data = (Map<String, Object>) res.get("data");
        List<Object> list = (List<Object>) data.get("list");
        if (list.size() == 0) {
            return new HashMap<String, String>();
        }
        Map<String, String> pkg = (Map<String, String>) list.get(0);
        return pkg;
    }

    @Test
    @Order(2)
    void test_update_rpmpkg() throws Exception {
        Map<String, String> pkg = searchPkg("testtest");
        String id = pkg.get("id");
        String name = pkg.get("name");

        MvcResult putResult = mockMvc.perform(MockMvcRequestBuilders.put("/rpmpkg")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\n" +
                        "  \"id\": \"%s\",\n" +
                        "  \"name\": \"%s\",\n" +
                        "  \"version\": \"update version\"\n" +
                        "}", id, name))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        Map<String, String> updatePkg = searchPkg("testtest");
        String version = updatePkg.get("version");
        assertEquals(version, "update version");
    }

    @Test
    @Order(3)
    void test_delete_rpmpkg() throws Exception {
        Map<String, String> pkg = searchPkg("testtest");
        String id = pkg.get("id");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/rpmpkg/{ids}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        
        Map<String, String> stillPkg = searchPkg("testtest");
        assertEquals(stillPkg.size(), 0);
    }
}
