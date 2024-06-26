package com.xantrix.webapp.config;

import com.xantrix.webapp.security.BearerTokenInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor(BearerTokenInterceptor bearerTokenInterceptor) {
        return bearerTokenInterceptor;
    }
}
