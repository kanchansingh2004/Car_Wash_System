package com.customer_service.customerservices.configuration;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

@Configuration
@EnableDiscoveryClient
public class UserConfiguration {
    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}
