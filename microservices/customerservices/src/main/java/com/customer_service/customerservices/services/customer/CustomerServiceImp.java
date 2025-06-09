package com.customer_service.customerservices.services.customer;

import com.customer_service.customerservices.dto.CustomerDTO;
import com.customer_service.customerservices.entity.CustomerEntity;
import com.customer_service.customerservices.exceptionhandling.AlreadyPresentException;
import com.customer_service.customerservices.exceptionhandling.NotFoundException;
import com.customer_service.customerservices.repository.CustomerRepo;
import com.customer_service.customerservices.util.AuthClientCall;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
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

    @Autowired
    AuthClientCall client;

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImp.class);

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
    public CustomerDTO addCustomer(String email, String profileImage) {
        // Get profile from Auth Service
        CustomerDTO authProfile = client.getProfile(email);
        if (authProfile == null) {
            throw new NotFoundException("User not found");
        }

        if(customerRepo.findByEmail(email).isPresent()){
            throw new AlreadyPresentException("User is already registered!!");
        }

        // Combine with profileImage
        CustomerDTO customerDTO = getCustomerDTO(profileImage, authProfile);

        // Completely customize the type map
        TypeMap<CustomerDTO, CustomerEntity> typeMap = modelMapper.typeMap(CustomerDTO.class, CustomerEntity.class);

        // Skip auto-mapping of 'id' to avoid String/String conflict
        typeMap.addMappings(mapper -> {
            mapper.skip(CustomerEntity::setId); // prevent mapping to 'id'
            mapper.map(CustomerDTO::getUserId, CustomerEntity::setUserId);
        });

        // Save to Customer DB (if needed)
        CustomerEntity entity = modelMapper.map(customerDTO, CustomerEntity.class);
        customerRepo.save(entity);

        return customerDTO;
    }

    private static CustomerDTO getCustomerDTO(String profileImage, CustomerDTO authProfile) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setEmail(authProfile.getEmail());
        customerDTO.setFirstName(authProfile.getFirstName());
        customerDTO.setLastName(authProfile.getLastName());
        customerDTO.setProfileImage(profileImage); // From request body
        customerDTO.setAddress(authProfile.getAddress());
        customerDTO.setPhone(authProfile.getPhone());
        customerDTO.setRole(authProfile.getRole());
        customerDTO.setUserId(authProfile.getUserId());
        return customerDTO;
    }

    @Override
    public CustomerDTO updateCustomer(String id, CustomerDTO userDTO) {
        return customerRepo.findByUserId(id).map(entity -> {
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
    public void deleteCustomer(String id){
        CustomerEntity dto = customerRepo.findByUserId(id).get();
        if(customerRepo.findByUserId(id).isEmpty()){
            log.warn("Washer with ID {} not found!", id);
            throw new NotFoundException("Washer with ID " + id + " Not exists");

        }
        log.info("Deleting user with ID: {}", id);
        customerRepo.deleteById(dto.getId());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(String id) {
        log.info("Fetching user with ID: {}", id);
        Optional<CustomerEntity> user = customerRepo.findByUserId(id);
        if(user.isEmpty()){
            log.warn("User with ID {} not found",id);
            throw new NotFoundException("User with ID " + id + " Not found!!");
        }
        return customerRepo.findByUserId(id)
                .map(user1 -> modelMapper.map(user1, CustomerDTO.class));
    }
}
