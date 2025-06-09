package com.customer_service.customerservices.dto;

public class CarDetailsDTO {
    private String carNumber;
    private String carModel;
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
