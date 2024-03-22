package com.tennissupplies.tennissuppliesbackend.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.tennissupplies.tennissuppliesbackend.repository")
public class DatabaseConfig {

}
