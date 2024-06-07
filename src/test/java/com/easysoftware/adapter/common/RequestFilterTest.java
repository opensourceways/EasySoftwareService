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
    public void testCheckDomain_NormalCase() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String allDomains = "test.com;test.cn";
        String[] domains = {"test.com", "test.cn"};
        RequestHeaderFilter filterConfig = new RequestHeaderFilter(allDomains);

        // 获取私有方法
        Method method = RequestHeaderFilter.class.getDeclaredMethod("checkDomain",
            String[].class, String.class);
        method.setAccessible(true); // 设置可访问私有方法

        // 以正常域名结尾
        assertTrue((boolean) method.invoke(filterConfig, domains, "www.test.com/"));

        // 以正常域名结尾并且带路径
        assertTrue((boolean) method.invoke(filterConfig, domains, "sub.test.cn/path"));

        assertTrue((boolean) method.invoke(filterConfig, domains, "sub.test.cn"));
        assertTrue((boolean) method.invoke(filterConfig, domains, "sub.test.cn/xxx?xx=xxx"));
        assertTrue((boolean) method.invoke(filterConfig, domains, "sub.test.cn/sss"));

        // 伪造域名
        assertFalse((boolean) method.invoke(filterConfig, domains, "xxxx.xxxx.test.cn@test.cn"));
        assertFalse((boolean) method.invoke(filterConfig, domains, "xxxxxtest.cn"));
        assertFalse((boolean) method.invoke(filterConfig, domains, "//sub.test.cn"));
        assertFalse((boolean) method.invoke(filterConfig, domains, "http://"));
        assertFalse((boolean) method.invoke(filterConfig, domains, "https://"));

        // 以正常域名结尾并且带参数
        assertTrue((boolean) method.invoke(filterConfig, domains, "sub.test.cn/path?test=xx"));

        // 不以 http:// 开头的错误域名
        assertFalse((boolean) method.invoke(filterConfig, domains, "www.test_error.com"));

        // 未匹配任何域名
        assertFalse((boolean) method.invoke(filterConfig, domains, "www.invalid.com"));

        // 空输入应返回 true
        assertTrue((boolean) method.invoke(filterConfig, domains, ""));
    }

    @Test
    public void testCheckDomain_NullDomains()  throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        RequestHeaderFilter filterConfig = new RequestHeaderFilter(null);

        // 获取私有方法
        Method method = RequestHeaderFilter.class.getDeclaredMethod("checkDomain",
            String[].class, String.class);
        method.setAccessible(true); // 设置可访问私有方法

        assertFalse((boolean) method.invoke(filterConfig, new String[]{}, "www.test.com"));
    }

    @Test
    public void testCheckDomain_EmptyDomains() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String allDomains = "";
        RequestHeaderFilter filterConfig = new RequestHeaderFilter(allDomains);

        // 获取私有方法
        Method method = RequestHeaderFilter.class.getDeclaredMethod("checkDomain",
            String[].class, String.class);
        method.setAccessible(true); // 设置可访问私有方法

        assertFalse((boolean) method.invoke(filterConfig, new String[]{}, "www.example.com"));
    }

    @Test
    public void testCheckDomain_ExceptionCase() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String allDomains = "test.com;test.cn";
        String[] domains = {"test.com", "test.cn"};
        RequestHeaderFilter filterConfig = new RequestHeaderFilter(allDomains);

        Method method = RequestHeaderFilter.class.getDeclaredMethod("checkDomain", String[].class, String.class);
        method.setAccessible(true);

        boolean result1 = (boolean) method.invoke(filterConfig, domains, "test.com");
        boolean result2 = (boolean) method.invoke(filterConfig, domains, "example.com");

        assertTrue(result1); // 应该返回true
        assertFalse(result2); // 应该返回false
    }
}
