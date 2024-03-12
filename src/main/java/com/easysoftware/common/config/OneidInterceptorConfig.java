package com.easysoftware.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.easysoftware.common.interceptor.OneidInterceptor;

@Configuration
public class OneidInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(OneidInterceptor())
                .addPathPatterns("/appVersion/**");
    }

    @Bean
    public OneidInterceptor OneidInterceptor() {
        return new OneidInterceptor();
    }
}
