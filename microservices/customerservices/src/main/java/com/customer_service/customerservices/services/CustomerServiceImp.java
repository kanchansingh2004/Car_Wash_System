package com.customer_service.customerservices.services;

import com.customer_service.customerservices.controller.UserController;
import com.customer_service.customerservices.dto.CustomerDTO;
import com.customer_service.customerservices.entity.CustomerEntity;
import com.customer_service.customerservices.exceptionhandling.UserNotFoundException;
import com.customer_service.customerservices.repository.CustomerRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service

public class CustomerServiceImp implements CustomerService {
    @Autowired
    private CustomerRepo customerRepo;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ModelMapper modelMapper; // ModelMapper for DTO conversion

    @Override
    public List<CustomerDTO> getAllCustomer() {
        log.info("Fetching all users from the database.");
        List<CustomerEntity>  users = customerRepo.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO userDTO) {
        if(userDTO == null){
            log.warn("Invalid user data received!");
            throw new IllegalArgumentException("User data cannot be null");
        }

        log.info("Adding new user: {}", userDTO);
        // Convert DTO to Entity
        CustomerEntity user = new CustomerEntity();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setProfileImage(userDTO.getProfileImage());
        customerRepo.save(user);
        // Save to DB
        return modelMapper.map(user,CustomerDTO.class);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO userDTO) {
        return customerRepo.findById(id).map(entity -> {
            entity.setFirstName(userDTO.getFirstName());
            entity.setLastName(userDTO.getLastName());
            entity.setPhone(userDTO.getPhone());
            entity.setAddress(userDTO.getAddress());
            entity.setEmail(userDTO.getEmail());
            entity.setProfileImage(userDTO.getProfileImage());
            CustomerEntity updatedEntity = customerRepo.save(entity);
            log.info("Employee details updated for ID: {}", id);
            return modelMapper.map(updatedEntity, CustomerDTO.class); // Mapping entity to DTO
        }).orElse(null);
    }

    @Override
    public void deleteCustomer(Long id){
        if(!customerRepo.existsById(id)){
            log.warn("User with ID {} not found!", id);
            throw new UserNotFoundException("User with ID " + id + " Not exists");

        }
        log.info("Deleting user with ID: {}", id);
        customerRepo.deleteById(id);
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(Long id) {
        log.info("Fetching user with ID: {}", id);
        Optional<CustomerEntity> user = customerRepo.findById(id);
        if(user.isEmpty()){
            log.warn("User with ID {} not found",id);
            throw new UserNotFoundException("User with ID " + id + " Not found!!");
        }
        return customerRepo.findById(id)
                .map(user1 -> modelMapper.map(user1, CustomerDTO.class));
    }
}
