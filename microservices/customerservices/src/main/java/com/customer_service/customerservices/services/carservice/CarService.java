package com.customer_service.customerservices.services.carservice;

import com.customer_service.customerservices.dto.CarDetailsDTO;
import com.customer_service.customerservices.entity.CarDetails;
import com.customer_service.customerservices.exceptionhandling.NotFoundException;
import com.customer_service.customerservices.repository.CarRepo;
import com.customer_service.customerservices.repository.CustomerRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarService {

    private static final Logger log = LoggerFactory.getLogger(CarService.class);

    @Autowired
    CarRepo carRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    ModelMapper modelMapper;

    // Add new car for customer
    public Map<String, String> addCarDetails(CarDetailsDTO dto, String userId) {
        log.info("Attempting to add car for userId: {}", userId);

        if (dto == null) {
            log.error("Car details are null");
            throw new NotFoundException("Error: No Car Details Provided");
        }

        if (carRepo.findByCarNumber(dto.getCarNumber()).isPresent()) {
            log.warn("Car already registered: {}", dto.getCarNumber());
            return Map.of("Message", "Car is already registered!!");
        }

        if (customerRepo.findByUserId(userId).isEmpty()) {
            log.warn("Customer not found: {}", userId);
            return Map.of("Message", "Customer is not registered!!");
        }

        CarDetails car = new CarDetails();
        car.setCarNumber(dto.getCarNumber());
        car.setCarModel(dto.getCarModel());
        car.setCarImages(dto.getCarImages());
        car.setCarId(generate());
        car.setUserId(userId);

        carRepo.save(car);
        log.info("Car registered successfully: {}", car.getCarId());

        return Map.of("Message", "Car register successfully", "CarId", car.getCarId());
    }

    // Generate unique Car ID
    public String generate() {
        return "CARID_" + UUID.randomUUID().toString().substring(0, 8);
    }

    // Get all cars
    public List<CarDetailsDTO> getAllCars() {
        log.info("Fetching all cars from DB");
        List<CarDetails> details = carRepo.findAll();
        return details.stream()
                .map(detail -> modelMapper.map(detail, CarDetailsDTO.class))
                .collect(Collectors.toList());
    }

    // Get cars for a specific user
    public List<CarDetailsDTO> getAllCarsByUser(String userId) {
        log.info("Fetching cars for userId: {}", userId);

        if (customerRepo.findByUserId(userId).isEmpty() || carRepo.findAllByUserId(userId).isEmpty()) {
            log.warn("No cars found for userId: {}", userId);
            return null;
        }

        List<CarDetails> details = carRepo.findAllByUserId(userId).get();
        return details.stream()
                .map(detail -> modelMapper.map(detail, CarDetailsDTO.class))
                .collect(Collectors.toList());
    }

    // Get specific car by number
    public CarDetailsDTO detail(String carNumber) {
        log.info("Fetching details for car number: {}", carNumber);

        Optional<CarDetails> detail = carRepo.findByCarNumber(carNumber);
        if (detail.isEmpty()) {
            log.error("Car not found: {}", carNumber);
            throw new NotFoundException("Car detail can not be find!!");
        }

        return modelMapper.map(detail.get(), CarDetailsDTO.class);
    }

    // Update existing car
    public CarDetailsDTO updateDetails(CarDetailsDTO dto, String carNumber) {
        log.info("Updating car with number: {}", carNumber);

        Optional<CarDetails> detail = carRepo.findByCarNumber(carNumber);
        if (detail.isEmpty()) {
            log.error("Car not found for update: {}", carNumber);
            throw new NotFoundException("Car detail can not be find!!");
        }

        CarDetails fetchCar = detail.get();
        fetchCar.setCarNumber(dto.getCarNumber());
        fetchCar.setCarModel(dto.getCarModel());
        fetchCar.setCarImages(dto.getCarImages());

        carRepo.save(fetchCar);
        log.info("Car updated successfully: {}", carNumber);

        return modelMapper.map(fetchCar, CarDetailsDTO.class);
    }

    // Delete a car
    public Map<String, String> deleteCar(String carNumber) {
        log.info("Attempting to delete car: {}", carNumber);

        Optional<CarDetails> detail = carRepo.findByCarNumber(carNumber);
        if (detail.isEmpty()) {
            log.error("Car not found for deletion: {}", carNumber);
            throw new NotFoundException("Car detail can not be find!!");
        }

        carRepo.deleteByCarNumber(carNumber);
        log.info("Car deleted successfully: {}", carNumber);

        return Map.of("Message", "Car details deleted successfully!!");
    }
}
