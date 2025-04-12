package com.washerservices.washer_services.services;
import com.washerservices.washer_services.dto.WasherDTO;
import com.washerservices.washer_services.entity.WasherEntity;
import com.washerservices.washer_services.exceptionhandling.WasherNotFoundException;
import com.washerservices.washer_services.repository.WasherRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
public class WasherServices {
    @Autowired
    WasherRepository washerRepo;

    private final ModelMapper modelMapper;

    // Constructor-based injection for ModelMapper
    public WasherServices(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //Add user
    public WasherEntity saveUser(WasherDTO washerDTO){
        if(washerDTO == null){
            log.warn("Invalid washer data received!");
            throw new IllegalArgumentException("Washer data cannot be null");
        }

        log.info("Adding new Washer: {}", washerDTO);
        WasherEntity washerEntity = modelMapper.map(washerDTO, WasherEntity.class);
        washerRepo.save(washerEntity);
        return washerEntity;
    }

    //Get all users
    public List<WasherDTO> getAllWasher(){
        log.info("Fetching all washers from the database.");
        List<WasherEntity>  users = washerRepo.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, WasherDTO.class))
                .collect(Collectors.toList());
    }
    //Get user by id
    public Optional<WasherDTO> getWasherById(Long id) {
        log.info("Fetching washer with ID: {}", id);
        Optional<WasherEntity> user = washerRepo.findById(id);
        if(user.isEmpty()){
            log.warn("Washer with ID {} not found",id);
            throw new WasherNotFoundException("Washer with ID " + id + " Not found!!");
        }
        return washerRepo.findById(id)
                .map(user1 -> modelMapper.map(user1, WasherDTO.class)); // Mapping entity to DTO
    }

    //Delete user
    public boolean deleteWasher(Long id){
        if(!washerRepo.existsById(id)){
            log.warn("Washer with ID {} not found!", id);
            throw new WasherNotFoundException("Washer with ID " + id + " Not exists");

        }
        log.info("Deleting washer with ID: {}", id);
        washerRepo.deleteById(id);
        return true;
    }

}
