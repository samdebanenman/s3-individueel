package com.tennissupplies.tennissuppliesbackend.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.Bean;
@Configuration
@EnableJpaRepositories(basePackages = "com.tennissupplies.tennissuppliesbackend.repository")
public class DatabaseConfig {

}
