package com.washerservices.washer_services.util;

import com.washerservices.washer_services.dto.WasherDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "AUTH-SERVICES", url = "http://localhost:8080")
public interface AuthClientCall {
    @GetMapping("/request/auth/washer/getProfile")
    WasherDTO getWasherProfile(@RequestParam String email);
}
