package com.adminservices.admin_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AdminServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminServicesApplication.class, args);
	}

}
