package com.customer_service.customerservices.util;

import com.customer_service.customerservices.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "AUTH-SERVICES", url = "http://localhost:8080")
public interface AuthClientCall {
    @GetMapping("/request/auth/customer/getProfile")
    CustomerDTO getProfile(@RequestParam String email);
}
