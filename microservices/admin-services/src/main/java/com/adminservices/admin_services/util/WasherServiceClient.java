package com.adminservices.admin_services.util;

import com.adminservices.admin_services.dto.WasherDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "washer-services", url = "http://localhost:8060/request/washer")
public interface WasherServiceClient {
    @GetMapping("/get")
    List<WasherDTO> getAllWashers();

    @GetMapping("/getBy/{id}")
    WasherDTO getWasherById(String id);
}
