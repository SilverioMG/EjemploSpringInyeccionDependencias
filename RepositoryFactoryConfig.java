package com.test.repositories;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.test")
public class RepositoryFactoryConfig {

    @Bean
    public RepositoryFactory repositoryFactory()
    {
        return new RepositoryFactory();
    }
}
