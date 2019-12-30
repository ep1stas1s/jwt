package com.auth.jwt.web.config;

import com.auth.jwt.web.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final String ALL_PATTERN = "/**";
    private static final String[] EXCLUDE_PATTERNS = {"/api/users", "/api/users/login", "/api/users/logout"};

    private final JwtInterceptor jwtInterceptor;

    public WebConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns(ALL_PATTERN)
                .excludePathPatterns(EXCLUDE_PATTERNS);
    }
}
