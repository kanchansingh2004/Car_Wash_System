package com.customer_service.customerservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CustomerservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerservicesApplication.class, args);
	}

}
