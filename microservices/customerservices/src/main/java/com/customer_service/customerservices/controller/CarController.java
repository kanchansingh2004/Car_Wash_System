package com.customer_service.customerservices.controller;

import com.customer_service.customerservices.dto.CarDetailsDTO;
import com.customer_service.customerservices.services.carservice.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/request/customer/car")
public class CarController {

    @Autowired
    CarService carService;

    private static final Logger LOGGER = Logger.getLogger(CarController.class.getName());

    // Add Car Details
    @PostMapping("/addCar")
    public ResponseEntity<Map<String, String>> addCar(@Valid @RequestBody CarDetailsDTO dto, @RequestParam String userId) {
        LOGGER.info("Received request to add car: " + dto.getCarNumber());
        return ResponseEntity.ok(carService.addCarDetails(dto,userId));
    }

    // Get All Cars
    @GetMapping("/getAllCars")
    public ResponseEntity<List<CarDetailsDTO>> getAllCars() {
        LOGGER.info("Received request to fetch all car details.");
        List<CarDetailsDTO> cars = carService.getAllCars();
        LOGGER.info("Total cars fetched: " + cars.size());
        return ResponseEntity.ok(cars);
    }

    // Get All Cars
    @GetMapping("/getAllCarsByUser")
    public ResponseEntity<List<CarDetailsDTO>> getAllCarsByUser(@RequestParam String userId) {
        LOGGER.info("Received request to fetch all car details.");
        List<CarDetailsDTO> cars = carService.getAllCarsByUser(userId);
        LOGGER.info("Total cars fetched: " + cars.size());
        return ResponseEntity.ok(cars);
    }

    // Get Car by Car Number
    @GetMapping("/getCar/{carNumber}")
    public ResponseEntity<CarDetailsDTO> getCarByNumber(@PathVariable String carNumber) {
        LOGGER.info("Received request to fetch car with number: " + carNumber);
        CarDetailsDTO car = carService.detail(carNumber);
        LOGGER.info("Car found: " + car.getCarNumber());
        return ResponseEntity.ok(car);
    }

    // Update Car Details
    @PutMapping("/updateCar/{carNumber}")
    public ResponseEntity<CarDetailsDTO> updateCarDetails(@Valid @RequestBody CarDetailsDTO dto,
                                                          @PathVariable String carNumber) {
        LOGGER.info("Received request to update car: " + carNumber);
        CarDetailsDTO updated = carService.updateDetails(dto, carNumber);
        LOGGER.info("Car updated successfully: " + updated.getCarNumber());
        return ResponseEntity.ok(updated);
    }

    // Delete Car by Car Number
    @DeleteMapping("/deleteCar/{carNumber}")
    public ResponseEntity<Map<String, String>> deleteCar(@PathVariable String carNumber) {
        LOGGER.info("Received request to delete car with number: " + carNumber);
        Map<String, String> response = carService.deleteCar(carNumber);
        LOGGER.info("Car deleted: " + carNumber);
        return ResponseEntity.ok(response);
    }
}
