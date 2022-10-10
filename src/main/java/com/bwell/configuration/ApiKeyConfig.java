package com.bwell.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class ApiKeyConfig {

    @Value("${API_KEY}")
    private String API_KEY;

    @Bean
    public String getApiKey(){
        return API_KEY;
    }
}
