package com.tennissupplies.tennissuppliesbackend.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
    // Configuration class for loading properties
}