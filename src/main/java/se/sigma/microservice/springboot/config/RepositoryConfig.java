package se.sigma.microservice.springboot.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = "se.sigma.microservice.springboot.model")
@EnableJpaRepositories(basePackages = "se.sigma.microservice.springboot.data.repository")
@EnableTransactionManagement
public class RepositoryConfig {}
