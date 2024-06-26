package com.xantrix.webapp.security;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class BearerTokenInterceptor implements RequestInterceptor {

    private final HttpServletRequest httpServletRequest;

    public BearerTokenInterceptor(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }
    @Override
    public void apply(RequestTemplate template) {
        String authHeader = httpServletRequest.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            template.header("Authorization", authHeader);
        }
    }
}
