package com.xantrix.webapp.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "gestuser")
@Data
public class UserConfig {

    private String srvUrl;
    private String username;
    private String password;
}
