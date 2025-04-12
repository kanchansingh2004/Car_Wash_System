package com.customer_service.customerservices.controller;

import com.customer_service.customerservices.dto.UserDTO;
import com.customer_service.customerservices.entity.UserEntity;
import com.customer_service.customerservices.exceptionhandling.UserNotFoundException;
import com.customer_service.customerservices.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
@RestController
@RequestMapping("/request/user")
public class UserController {
    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    //Random
    @GetMapping("/rand")
    public String hello(){
        return "Hello";
    }
    //Add user
    @PostMapping("/post")
    public ResponseEntity<UserEntity> addUser(@Valid @RequestBody UserDTO userDTO){
        log.info("Received request to save user: {}", userDTO);
        UserEntity savedUser = userService.saveUser(userDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    //Get all users
    @GetMapping("/get")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        log.info("Fetching all users records.");
        return ResponseEntity.ok(userService.getAllUsers());
    }
    //Get user by id
    @GetMapping("/getBy/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        log.info("Fetching the user details with id {}", id);
        UserDTO userDTO = userService.getUSerById(id).orElseThrow(
                () -> new UserNotFoundException("User with ID "+ id + " Not found!!")
        );

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    //Delete user
    @DeleteMapping("/deleteBy/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        log.info("Deleting the user with id {}", id);
        userService.deleteUser(id);
        return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
    }
    //Update user
    //Find by name
    //Find by email
}
