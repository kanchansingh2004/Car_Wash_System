package com.customer_service.customerservices;

import com.customer_service.customerservices.dto.CarDetailsDTO;
import com.customer_service.customerservices.repository.CarRepo;
import com.customer_service.customerservices.services.carservice.CarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CarServiceTest {
    @Autowired
    private CarService carService;

    @Autowired
    private CarRepo carDetailsRepository;

    @Test
    public void testSaveCarDetails() {
        CarDetailsDTO carDetailsDTO = new CarDetailsDTO();
        carDetailsDTO.setCarModel("Hyundai");
        carDetailsDTO.setCarNumber("MH12AB1234");

        CarDetailsDTO result = carService.addCarDetails(carDetailsDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Hyundai", result.getCarModel());
    }
}
