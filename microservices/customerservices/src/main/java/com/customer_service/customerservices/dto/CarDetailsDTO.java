package com.customer_service.customerservices.dto;

import java.util.List;

public class CarDetailsDTO {
    private String carNumber;
    private String carModel;
    private List<String> carImages;

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

    public List<String> getCarImages() {
        return carImages;
    }

    public void setCarImages(List<String> carImages) {
        this.carImages = carImages;
    }
}
