package com.easysoftware.controller;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;
import java.util.Set;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.easysoftware.entity.dto.InputApplicationPackage;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

//SpringBoot1.4版本之前用的是SpringJUnit4ClassRunner.class
@RunWith(SpringRunner.class)
//SpringBoot1.4版本之前用的是@SpringApplicationConfiguration(classes = Application.class)
@SpringBootTest
//测试环境使用，用来表示测试环境使用的ApplicationContext将是WebApplicationContext类型的
@WebAppConfiguration
class AppPackageControllerTest {
 
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
 
    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // 输入参数不符合@Valid注解标注的要求
    @Test
    void test_http_result_of_incorrect_input_parameters() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/apppkg")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\r\n    \r\n    \"name\": \"ouvmindvtcboewqlsfklnlhxrrnaljzdcyljlwuxjbldvvfwhlcoxtuinwbjrcneeynpkgdxrekppfzfrjgekyrchhtntidhngendaabgijpvfdepwvlhkqbybhznyjqyuzpeoigcdpcgwqjtgepaqvclubgoqrgticlnwzcyhabpkixxdxooiwflptmluzyknutuvrbsbgzlbeyhafhttgumlggkdcgipdorbpetazxxkdjgkdgrzuikhefucry\",\r\n    \"version\": \"ouvmindvtcboewqlsfklnlhxrrnaljzdcyljlwuxjbldvvfwhlcoxtuinwbjrcneeynpkgdxrekppfzfrjgekyrchhtntidhngendaabgijpvfdepwvlhkqbybhznyjqyuzpeoigcdpcgwqjtgepaqvclubgoqrgticlnwzcyhabpkixxdxooiwflptmluzyknutuvrbsbgzlbeyhafhttgumlggkdcgipdorbpetazxxkdjgkdgrzuikhefucry\"\r\n}\r\n")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        //得到返回结果
        String content = mvcResult.getResponse().getContentAsString(Charset.defaultCharset());
        assertEquals("{\"status\":400,\"msg\":", content.substring(0, 20));
    }

    @Test
    // 测试name
    void test_incorrect_input_parameters() {
        InputApplicationPackage pkg = new InputApplicationPackage();
        // name字段字节长度为256
        pkg.setName("ouvmindvtcboewqlsfklnlhxrrnaljzdcyljlwuxjbldvvfwhlcoxtuinwbjrcneeynpkgdxrekppfzfrjgekyrchhtntidhngendaabgijpvfdepwvlhkqbybhznyjqyuzpeoigcdpcgwqjtgepaqvclubgoqrgticlnwzcyhabpkixxdxooiwflptmluzyknutuvrbsbgzlbeyhafhttgumlggkdcgipdorbpetazxxkdjgkdgrzuikhefucry");
        // pkg.setVersion(null);
        // pkg.setPkgUrl("https://repo.openeuler.openatom.cn/openEuler-20.03-LTS-SP3/update/source/Packages/curl-7.71.1-21.oe1.src.rpm");
        // pkg.setSrcUrl("hgwgyh");

        // SimilarPkg sPkg1 = new SimilarPkg();
        // ValidList<SimilarPkg> list = new ValidList<>();
        // list.add(sPkg1);
        // pkg.setSimilarPgks(list);

        // assertThrows(, null)

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<InputApplicationPackage>> violates = validator.validate(pkg);
        for (ConstraintViolation<InputApplicationPackage> violate : violates) {
            String fieldName = ((PathImpl) violate.getPropertyPath()).getLeafNode().getName();
            String errorMessage = violate.getMessage();
            Object obj = violate.getInvalidValue();
            
            if ("version".equals(fieldName)) {
                assertNull(obj);
                assertEquals(errorMessage, "version can not be null");
            }
            if ("pkgName".equals(fieldName)) {
                assertNull(obj);
                assertEquals(errorMessage, "pkg name can not be null");
            }
            if ("srcUrl".equals(fieldName)) {
                assertEquals(errorMessage, "需要是一个合法的URL");
            }
            if ("name".equals(fieldName)) {
                String str = (String) violate.getInvalidValue();
                assertTrue(255 < str.length());
                assertEquals(errorMessage, "the length of name can not exceed 255");
            }
        }
    }

 

 
    // @Test
    // void contextLoads() throws Exception {
    //     /**
    //      * 1、mockMvc.perform执行一个请求。
    //      * 2、MockMvcRequestBuilders.get("XXX")构造一个请求。
    //      * 3、ResultActions.param添加请求传值
    //      * 4、ResultActions.accept(MediaType.TEXT_HTML_VALUE))设置返回类型
    //      * 5、ResultActions.andExpect添加执行完成后的断言。
    //      * 6、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情
    //      *   比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
    //      * 5、ResultActions.andReturn表示执行完成后返回相应的结果。
    //      */
    //     MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/apppkg/hello")
    //             //.param("name", "lvgang")
    //             .accept(MediaType.APPLICATION_JSON))
    //             //等同于Assert.assertEquals(200,status);
    //             .andExpect(MockMvcResultMatchers.status().isOk())
    //             //等同于 Assert.assertEquals("hello world!",content);
    //             .andExpect(MockMvcResultMatchers.content().string("hello world!"))
    //             .andDo(MockMvcResultHandlers.print())
    //             .andReturn();
    //     //得到返回代码
    //     int status = mvcResult.getResponse().getStatus();
    //     //得到返回结果
    //     String content = mvcResult.getResponse().getContentAsString();
    //     //断言，判断返回代码是否正确
    //     Assert.assertEquals(200, status);
    //     //断言，判断返回的值是否正确
    //     Assert.assertEquals("hello world!", content);
    // }
 
}


