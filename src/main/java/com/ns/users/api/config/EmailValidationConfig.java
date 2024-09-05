package com.ns.users.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailValidationConfig {

    @Value("${security.email.regex}")
    private String emailRegex;

    public String getEmailRegex() {
        return emailRegex;
    }
}
