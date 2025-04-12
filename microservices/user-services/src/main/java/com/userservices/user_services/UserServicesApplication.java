package com.userservices.user_services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class UserServicesApplication {

	public static void main(String[] args) {
		log.info("Starting Application.......");
		SpringApplication.run(UserServicesApplication.class, args);
		log.info("Application in process.......");
	}

}
