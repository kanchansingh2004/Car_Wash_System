package com.adminservices.admin_services.controller;

import com.adminservices.admin_services.entity.AdminCustomerEntity;
import com.adminservices.admin_services.entity.AdminWasherEntity;
import com.adminservices.admin_services.services.washer.WasherAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/request/admin/washer")
public class WasherAdminController {

    private static final Logger logger = LoggerFactory.getLogger(WasherAdminController.class);

    @Autowired
    private WasherAdminService washerAdminService;

    @GetMapping("/random")
    public String callRandom(){
        logger.info("Random endpoint hit - /random");
        return "hello";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Map<String, Object>>> getAllWashers() {
        logger.info("Fetching all washers");
        List<Map<String, Object>> washer = washerAdminService.getAllWashers();
        logger.info("Fetched {} washers", washer.size());
        return new ResponseEntity<>(washer, HttpStatus.OK);
    }

    @GetMapping("/getBy/{id}")
    public ResponseEntity<Map<String, Object>> getWasherById(@PathVariable String id) {
        logger.info("Fetching washer with ID: {}", id);
        return new ResponseEntity<>(washerAdminService.getWasherById(id), HttpStatus.OK);
    }

    @GetMapping("/addAllWashers")
    public ResponseEntity<List<AdminWasherEntity>> addWasher() {
        logger.info("Adding all washers from Washer Service to Admin DB");
        List<AdminWasherEntity> result = washerAdminService.addWasherFromWasherService();
        logger.info("Added {} washers to Admin DB", result.size());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/updateStatus")
    public ResponseEntity<AdminWasherEntity> updateActiveStatus(@RequestParam String washerId, @RequestParam Boolean activeStatus){
        logger.info("Updating active status of customer ID {}", washerId);
        return ResponseEntity.ok(washerAdminService.updateStatus(washerId,activeStatus));
    }
}
