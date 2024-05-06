package com.easysoftware.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.easysoftware.common.interceptor.OneidInterceptor;

@Configuration
public class OneidInterceptorConfig implements WebMvcConfigurer {

    /**
     * Add interceptors to the InterceptorRegistry.
     *
     * @param registry The InterceptorRegistry used to register interceptors.
     */
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(oneidInterceptor())
                .addPathPatterns("/appVersion/**");
    }

    /**
     * Configures a OneidInterceptor bean.
     *
     * @return The configured OneidInterceptor bean.
     */
    @Bean
    public OneidInterceptor oneidInterceptor() {
        return new OneidInterceptor();
    }
}
