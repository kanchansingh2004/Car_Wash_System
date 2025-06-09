package com.adminservices.admin_services.controller;

import com.adminservices.admin_services.entity.AdminCarEntity;
import com.adminservices.admin_services.services.car.CarAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/request/admin/customer/car")
public class CarAdminController {

    private static final Logger logger = LoggerFactory.getLogger(CarAdminController.class);

    @Autowired private CarAdminService carAdminService;

    @GetMapping("/random")
    public String callRandom(){
        logger.info("Random endpoint hit - /random");
        return "hello";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Map<String, Object>>> getAllCars() {
        logger.info("Fetching all cars");
        List<Map<String, Object>> cars = carAdminService.getAllCars();
        logger.info("Fetched {} cars", cars.size());
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @GetMapping("/getCar/{carNumber}")
    public ResponseEntity<Map<String, Object>> getCarByCarNumber(@PathVariable String carNumber) {
        logger.info("Fetching car with carNumber: {}", carNumber);
        return new ResponseEntity<>(carAdminService.getCarByCarNumber(carNumber), HttpStatus.OK);
    }

    @GetMapping("/addAllCars")
    public ResponseEntity<List<AdminCarEntity>> addCar() {
        logger.info("Adding all cars from Car Service to Admin DB");
        List<AdminCarEntity> result = carAdminService.addCarFromCarService();
        logger.info("Added {} cars to Admin DB", result.size());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAllCarsByUser")
    public ResponseEntity<List<Map<String, Object>>> getCarByUserId(@RequestParam String userId) {
        logger.info("Fetching all cars for userId: {}", userId);
        return new ResponseEntity<>(carAdminService.getAllCarsByUser(userId), HttpStatus.OK);
    }

    @GetMapping("/updateStatus")
    public ResponseEntity<AdminCarEntity> updateActiveStatus(@RequestParam String carId, @RequestParam Boolean activeStatus){
        logger.info("Updating active status of car ID {}", carId);
        return ResponseEntity.ok(carAdminService.updateStatus(carId,activeStatus));
    }
}
