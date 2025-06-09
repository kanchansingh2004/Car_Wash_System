package com.adminservices.admin_services.util;

import com.adminservices.admin_services.dto.CarDetailsDTO;
import com.adminservices.admin_services.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "customer-services", url = "http://localhost:8020/request/customer")
public interface CustomerServiceClient {
    @GetMapping("/get")
    List<CustomerDTO> getAllCustomers();

    @GetMapping("/getBy/{id}")
    CustomerDTO getCustomerById(@PathVariable String id);

    //Get All Cars
    @GetMapping("/car/getAllCars")
    List<CarDetailsDTO> getAllCars();

    // Get Car by Car Number
    @GetMapping("/car/getCar/{carNumber}")
    CarDetailsDTO getCarByNumber(@PathVariable String carNumber);

    // Get All Cars
    @GetMapping("/car/getAllCarsByUser")
    List<CarDetailsDTO> getAllCarsByUser(@RequestParam String userId);
}
