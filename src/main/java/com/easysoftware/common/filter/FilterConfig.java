package com.easysoftware.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * 加载所有的filter并指定filter顺序.
 */
@Slf4j
@Configuration
public class FilterConfig {
    /**
     * Referer pass domain.
     */
    @Value("${cookie.token.domains}")
    private String allowDomains;

    /**
     * 编码过滤器.
     *
     * @return filterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean encodingFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        filterRegistrationBean.setFilter(characterEncodingFilter);
        // 顺序
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    /**
     * 请求头拦截器.
     *
     * @return filterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean requestHeaderFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        RequestHeaderFilter requestHeaderFilter = new RequestHeaderFilter(allowDomains);
        filterRegistrationBean.setFilter(requestHeaderFilter);
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    /**
     * 响应请求头设置过滤器.
     *
     * @return filterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean responseHeaderFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        ResponseHeaderFilter crossFilter = new ResponseHeaderFilter();
        filterRegistrationBean.setFilter(crossFilter);
        filterRegistrationBean.setOrder(3);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
