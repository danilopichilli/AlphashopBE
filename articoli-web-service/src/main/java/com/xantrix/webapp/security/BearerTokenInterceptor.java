package com.xantrix.webapp.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class BearerTokenInterceptor implements ClientHttpRequestInterceptor {

    private String token;

    public BearerTokenInterceptor(String token) {
        this.token = token;
    }
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + this.token);
        return execution.execute(request, body);
    }
}
