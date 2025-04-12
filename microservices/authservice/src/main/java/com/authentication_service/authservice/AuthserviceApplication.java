package com.authentication_service.authservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthserviceApplication {

	public static void main(String[] args) {

		final Logger log = LoggerFactory.getLogger(AuthserviceApplication.class);

		log.info("Starting Application");
		SpringApplication.run(AuthserviceApplication.class, args);
		log.info("Application in process");
	}

}
