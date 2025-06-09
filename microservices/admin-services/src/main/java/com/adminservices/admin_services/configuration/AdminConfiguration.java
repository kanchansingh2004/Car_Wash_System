package com.adminservices.admin_services.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminConfiguration {
    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}
