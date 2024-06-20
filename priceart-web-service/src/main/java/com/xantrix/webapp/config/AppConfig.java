package com.xantrix.webapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application")
public class AppConfig {

    private String listino;

    public String getListino() {
        return listino;
    }

    public void setListino(String listino) {
        this.listino = listino;
    }
}
