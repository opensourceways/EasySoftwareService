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


package com.easysoftware.adapter.common;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.easysoftware.common.filter.RequestHeaderFilter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;



@SpringBootTest
public class RequestFilterTest {

    @Test
    public void testCheckDomain() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String allDomains = "test.com;test.cn";
        String[] domains = {"test.com", "test.cn"};
        RequestHeaderFilter filterConfig = new RequestHeaderFilter(allDomains);

        // 获取私有方法
        Method method = RequestHeaderFilter.class.getDeclaredMethod("checkDomain", String[].class, String.class);
        method.setAccessible(true); // 设置可访问私有方法

        // 调用私有方法，并传入参数
        boolean result1 = (boolean) method.invoke(filterConfig, domains, "test.com");
        boolean result2 = (boolean) method.invoke(filterConfig, domains, "example.com");

        // 断言
        assertTrue(result1); // 应该返回true
        assertFalse(result2); // 应该返回false
    }
}
