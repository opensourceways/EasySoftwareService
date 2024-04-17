package com.easysoftware.adapter.common;

import static org.mockito.Mockito.when;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.easysoftware.application.applicationpackage.ApplicationPackageService;
import com.easysoftware.application.applicationpackage.dto.InputApplicationPackage;
import com.easysoftware.application.epkgpackage.EPKGPackageService;
import com.easysoftware.application.rpmpackage.RPMPackageService;
import com.easysoftware.application.rpmpackage.dto.InputRPMPackage;
import com.easysoftware.common.entity.ResultVo;
import com.easysoftware.common.utils.CommonUtil;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.common.utils.ResultUtil;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class InputPkgTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    private MockMvc mockMvc;

    @MockBean
    private ApplicationPackageService appService;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void test_input_app_pkg() throws Exception {
        // 正常情况
        InputApplicationPackage input = new InputApplicationPackage();
        input.setName("xx");
        input.setCategory("大数据");
        input.setMaintainerEmail("username@domain.com");
        input.setSrcDownloadUrl("https://www.baidu.com");
        when(appService.insertAppPkg(input)).thenReturn(ResultUtil.success(HttpStatus.OK));
        ResultVo res = CommonUtil.executePost(mockMvc, "/apppkg", ObjectMapperUtil.writeValueAsString(input));
        CommonUtil.assertOk(res);

        // 不满足@Size
        input = new InputApplicationPackage();
        input.setName(StringUtils.repeat("x", 256));
        input.setCategory("大数据");
        when(appService.insertAppPkg(input)).thenReturn(ResultUtil.success(HttpStatus.OK));
        res = CommonUtil.executePost(mockMvc, "/apppkg", ObjectMapperUtil.writeValueAsString(input));
        CommonUtil.assert400(res);

        // 不满足EnumValue
        input = new InputApplicationPackage();
        input.setName("xx");
        input.setCategory("test");
        when(appService.insertAppPkg(input)).thenReturn(ResultUtil.success(HttpStatus.OK));
        res = CommonUtil.executePost(mockMvc, "/apppkg", ObjectMapperUtil.writeValueAsString(input));
        CommonUtil.assert400(res);

        // 不满足@Email
        input = new InputApplicationPackage();
        input.setName("xx");
        input.setCategory("大数据");
        input.setMaintainerEmail("username@@domain.com");
        when(appService.insertAppPkg(input)).thenReturn(ResultUtil.success(HttpStatus.OK));
        res = CommonUtil.executePost(mockMvc, "/apppkg", ObjectMapperUtil.writeValueAsString(input));
        CommonUtil.assert400(res);

        // 不满足@URL
        input = new InputApplicationPackage();
        input.setName("xx");
        input.setCategory("大数据");
        input.setMaintainerEmail("username@@domain.com");
        input.setSrcDownloadUrl("wwwxxx.swww");
        when(appService.insertAppPkg(input)).thenReturn(ResultUtil.success(HttpStatus.OK));
        res = CommonUtil.executePost(mockMvc, "/apppkg", ObjectMapperUtil.writeValueAsString(input));
        CommonUtil.assert400(res);
    }
}
