package com.washerservices.washer_services.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

@Configuration
public class WasherConfiguration {
    @Bean
    public ModelMapper getModelMapper(){return new ModelMapper();}
}
