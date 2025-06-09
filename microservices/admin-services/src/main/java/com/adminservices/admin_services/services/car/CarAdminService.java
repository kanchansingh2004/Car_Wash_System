package com.adminservices.admin_services.services.car;

import com.adminservices.admin_services.dto.CarDetailsDTO;
import com.adminservices.admin_services.entity.AdminCarEntity;
import com.adminservices.admin_services.exceptionhandling.NotFoundException;
import com.adminservices.admin_services.repository.AdminCarRepository;
import com.adminservices.admin_services.util.CustomerServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarAdminService {
    private static final Logger logger = LoggerFactory.getLogger(CarAdminService.class);

    @Autowired
    private AdminCarRepository adminCarRepository;

    @Autowired
    private CustomerServiceClient customerServiceClient;

    public List<Map<String, Object>> getAllCars() {
        logger.info("Fetching all cars from Customer Service and merging with Admin DB data.");
        List<Map<String, Object>> responseList = new ArrayList<>();
        List<CarDetailsDTO> carDTOList = customerServiceClient.getAllCars();

        for (CarDetailsDTO car : carDTOList) {
            Optional<AdminCarEntity> adminCar = adminCarRepository.findByCarId(car.getCarId());
            Map<String, Object> carData = new HashMap<>();
            carData.put("carDetails", car);
            carData.put("activeStatus", adminCar.map(AdminCarEntity::getActiveStatus).orElse(null));
            responseList.add(carData);
        }

        logger.info("Fetched and prepared {} car records.", responseList.size());
        return responseList;
    }

    public Map<String, Object> getCarByCarNumber(String carNumber) {
        logger.info("Fetching car by carNumber: {}", carNumber);
        CarDetailsDTO car = customerServiceClient.getCarByNumber(carNumber);

        AdminCarEntity adminCar = adminCarRepository.findByCarId(car.getCarId())
                .orElseThrow(() -> {
                    logger.error("Car not found in Admin DB with carId: {}", car.getCarId());
                    return new RuntimeException("Car not found in Admin database");
                });

        Map<String, Object> response = new HashMap<>();
        response.put("carDetails", car);
        response.put("activeStatus", adminCar.getActiveStatus());

        logger.info("Car fetched successfully: {}", response);
        return response;
    }

    public List<AdminCarEntity> addCarFromCarService() {
        logger.info("Adding all cars from Car Service into Admin DB.");
        List<AdminCarEntity> list = new ArrayList<>();
        List<CarDetailsDTO> carDTOList = customerServiceClient.getAllCars();

        for (CarDetailsDTO car : carDTOList) {
            AdminCarEntity entity = new AdminCarEntity();
            entity.setCarId(car.getCarId());
            entity.setActiveStatus(true);
            adminCarRepository.save(entity);
            list.add(entity);
        }

        logger.info("Successfully added {} cars to Admin DB.", list.size());
        return list;
    }

    public List<Map<String, Object>> getAllCarsByUser(String userId) {
        logger.info("Fetching all cars by userId: {}", userId);
        List<Map<String, Object>> responseList = new ArrayList<>();
        List<CarDetailsDTO> carDTOList = customerServiceClient.getAllCarsByUser(userId);

        for (CarDetailsDTO car : carDTOList) {
            Optional<AdminCarEntity> adminCar = adminCarRepository.findByCarId(car.getCarId());
            Map<String, Object> carData = new HashMap<>();
            carData.put("carDetails", car);
            carData.put("activeStatus", adminCar.map(AdminCarEntity::getActiveStatus).orElse(null));
            responseList.add(carData);
        }

        logger.info("Fetched {} cars for userId {}", responseList.size(), userId);
        return responseList;
    }

    public AdminCarEntity updateStatus(String carId, Boolean activeStatus) {
        AdminCarEntity entity = adminCarRepository.findByCarId(carId).orElseThrow(() -> {
            logger.error("Car not found with ID: {}", carId);
            return new NotFoundException("Car not found with ID: " + carId);
        });
        entity.setActiveStatus(activeStatus);
        adminCarRepository.save(entity);
        return entity;
    }
}
