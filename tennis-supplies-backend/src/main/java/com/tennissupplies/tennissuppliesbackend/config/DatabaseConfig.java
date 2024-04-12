package com.tennissupplies.tennissuppliesbackend.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@Configuration
@EnableJpaRepositories(basePackages = "com.tennissupplies.tennissuppliesbackend.repository")
public class DatabaseConfig {

}
