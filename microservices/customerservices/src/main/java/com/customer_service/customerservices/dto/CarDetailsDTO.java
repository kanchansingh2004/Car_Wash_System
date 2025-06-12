package com.customer_service.customerservices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CarDetailsDTO {
    @NotBlank(message = "Car number is required")
    @Pattern(regexp = "^[A-Z]{2}[0-9]{1,2}[A-Z]{1,2}[0-9]{4}$", message = "Invalid car number format")
    private String carNumber;

    @NotBlank(message = "Car model is required")
    @Pattern(regexp = "^[A-Za-z0-9 ]{2,}$", message = "Invalid car model")
    private String carModel;

    @Pattern(regexp = "\\.jpg$", message = "Invalid image format")
    private String carImages;


    private String carId;
    private String UserId;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarImages() {
        return carImages;
    }

    public void setCarImages(String carImages) {
        this.carImages = carImages;
    }
}
