package com.laboratory.demo.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Filter())
                .addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/style/**")
                .excludePathPatterns("/image/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/visitor")
                .excludePathPatterns("/logo.png");
    }
}
