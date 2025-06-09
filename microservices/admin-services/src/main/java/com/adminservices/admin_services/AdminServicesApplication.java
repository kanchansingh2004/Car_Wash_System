package com.adminservices.admin_services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AdminServicesApplication {
	private static final Logger log = LoggerFactory.getLogger(AdminServicesApplication.class);
	public static void main(String[] args) {
		log.info("Application started........");
		SpringApplication.run(AdminServicesApplication.class, args);
		log.info("Application in process......");
	}

}
