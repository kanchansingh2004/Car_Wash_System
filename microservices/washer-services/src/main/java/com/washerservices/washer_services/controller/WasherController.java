package com.washerservices.washer_services.controller;
import com.washerservices.washer_services.dto.WasherDTO;
import com.washerservices.washer_services.entity.WasherEntity;
import com.washerservices.washer_services.exceptionhandling.WasherNotFoundException;
import com.washerservices.washer_services.services.WasherServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@Slf4j
@RestController
@RequestMapping("/request/washer")
public class WasherController {
    @Autowired
    private WasherServices washerServices;

    //Add user
    @PostMapping
    public ResponseEntity<WasherEntity> addWasher(@RequestBody WasherDTO washerDTO){
        log.info("Received request to save washer: {}", washerDTO);
        WasherEntity user = washerServices.saveUser(washerDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    //Get all users
    @GetMapping
    public ResponseEntity<List<WasherDTO>> getAllWashers(){
        log.info("Fetching all washers records.");
        return ResponseEntity.ok(washerServices.getAllWasher());
    }
    //Get user by id
    @GetMapping("/{id}")
    public ResponseEntity<WasherDTO> getWasherById(@PathVariable Long id){
        log.info("Fetching the washer details with id {}", id);
        WasherDTO washerDTO = washerServices.getWasherById(id).orElseThrow(
                () -> new WasherNotFoundException("Washer with ID "+ id + " Not found!!")
        );

        return new ResponseEntity<>(washerDTO, HttpStatus.OK);
    }
    //Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWasher(@PathVariable Long id) {
        log.info("Deleting the washer with id {}", id);
        washerServices.deleteWasher(id);
        return new ResponseEntity<>("Washer successfully deleted!", HttpStatus.OK);
    }
}
