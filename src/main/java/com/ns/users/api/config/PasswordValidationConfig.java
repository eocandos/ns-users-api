package com.ns.users.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PasswordValidationConfig {

    @Value("${security.password.regex}")
    private String passwordRegex;

    public String getPasswordRegex() {
        return passwordRegex;
    }
}
