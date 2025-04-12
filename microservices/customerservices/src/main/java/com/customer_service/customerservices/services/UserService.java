package com.customer_service.customerservices.services;

import com.customer_service.customerservices.controller.UserController;
import com.customer_service.customerservices.dto.UserDTO;
import com.customer_service.customerservices.entity.UserEntity;
import com.customer_service.customerservices.exceptionhandling.UserNotFoundException;
import com.customer_service.customerservices.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    private final ModelMapper modelMapper; // ModelMapper for DTO conversion

    // Constructor-based injection for ModelMapper
    public UserService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //Add user
    public UserEntity saveUser(UserDTO userDTO){
        if(userDTO == null){
            log.warn("Invalid user data received!");
            throw new IllegalArgumentException("User data cannot be null");
        }

        log.info("Adding new user: {}", userDTO);
        // Convert DTO to Entity
        UserEntity user = new UserEntity();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        // Save to DB
        return userRepo.save(user);
    }

    //Get all users
    public List<UserDTO> getAllUsers(){
        log.info("Fetching all users from the database.");
        List<UserEntity>  users = userRepo.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
    //Get user by id
    public Optional<UserDTO> getUSerById(Long id) {
        log.info("Fetching user with ID: {}", id);
        Optional<UserEntity> user = userRepo.findById(id);
        if(user.isEmpty()){
            log.warn("User with ID {} not found",id);
            throw new UserNotFoundException("User with ID " + id + " Not found!!");
        }
        return userRepo.findById(id)
                .map(user1 -> modelMapper.map(user1, UserDTO.class)); // Mapping entity to DTO
    }

    //Delete user
    public boolean deleteUser(Long id){
        if(!userRepo.existsById(id)){
            log.warn("User with ID {} not found!", id);
            throw new UserNotFoundException("User with ID " + id + " Not exists");

        }
        log.info("Deleting user with ID: {}", id);
        userRepo.deleteById(id);
        return true;
    }
    //Update user
    //Find by name
    //Find by email
}
