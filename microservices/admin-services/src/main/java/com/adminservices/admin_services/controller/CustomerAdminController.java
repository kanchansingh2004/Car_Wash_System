package com.adminservices.admin_services.controller;

import com.adminservices.admin_services.dto.CustomerDTO;
import com.adminservices.admin_services.entity.AdminCarEntity;
import com.adminservices.admin_services.entity.AdminCustomerEntity;
import com.adminservices.admin_services.services.customer.CustomerAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/request/admin/customer")
public class CustomerAdminController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerAdminController.class);

    @Autowired
    private CustomerAdminService customerAdminService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Map<String, Object>>> getAllCustomers() {
        logger.info("Fetching all customers");
        List<Map<String, Object>> customers = customerAdminService.getAllCustomers();
        logger.info("Fetched {} customers", customers.size());
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/getBy/{id}")
    public ResponseEntity<Map<String, Object>> getCustomerById(@PathVariable String id) {
        logger.info("Fetching customer with ID: {}", id);
        return new ResponseEntity<>(customerAdminService.getCustomerById(id), HttpStatus.OK);
    }

    @GetMapping("/addAllCustomers")
    public ResponseEntity<List<AdminCustomerEntity>> addCustomer() {
        logger.info("Adding all customers from Customer Service to Admin DB");
        List<AdminCustomerEntity> result = customerAdminService.addCustomerFromCustomerService();
        logger.info("Added {} customers to Admin DB", result.size());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/updateStatus")
    public ResponseEntity<AdminCustomerEntity> updateActiveStatus(@RequestParam String customerId, @RequestParam Boolean activeStatus){
        logger.info("Updating active status of customer ID {}", customerId);
        return ResponseEntity.ok(customerAdminService.updateStatus(customerId,activeStatus));
    }
}

