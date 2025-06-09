package com.customer_service.customerservices.controller;

import com.customer_service.customerservices.dto.CustomerDTO;
import com.customer_service.customerservices.exceptionhandling.NotFoundException;
import com.customer_service.customerservices.services.customer.CustomerServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/request/customer")
public class CustomerController {
    @Autowired
    private CustomerServiceImp customerService;

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);


    //Random
    @GetMapping("/rand")
    public String hello(){
        return "Hello";
    }

    //Add user
    @PostMapping("/post")
    public ResponseEntity<CustomerDTO> addUser(@RequestParam String email, @RequestBody Map<String,String> requestBody){
        log.info("Received request to save user for email: {}", email);
        String profileImage = requestBody.get("profileImage");
        CustomerDTO savedUser = customerService.addCustomer(email, profileImage);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    //Get all users
    @GetMapping("/get")
    public ResponseEntity<List<CustomerDTO>> getAllUsers(){
        log.info("Fetching all users records.");
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

    //Get user by id
    @GetMapping("/getBy/{id}")
    public ResponseEntity<CustomerDTO> getUserById(@PathVariable String id){
        log.info("Fetching the user details with id {}", id);
        CustomerDTO userDTO = customerService.getCustomerById(id).orElseThrow(
                () -> new NotFoundException("User with ID "+ id + " Not found!!")
        );

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBy/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        log.info("Deleting the user with id {}", id);
        customerService.deleteCustomer(id);
        return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable String id, @RequestBody CustomerDTO userDTO){
        log.info("Updating the customer information with id {}", id);
        return ResponseEntity.ok(customerService.updateCustomer(id,userDTO));
    }
}
