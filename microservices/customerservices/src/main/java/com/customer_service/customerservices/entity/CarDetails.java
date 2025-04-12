package com.customer_service.customerservices.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "car_details")
public class CarDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String carNumber;
    private String carModel;
    private List<String> carImages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<String> getCarImages() {
        return carImages;
    }

    public void setCarImages(List<String> carImages) {
        this.carImages = carImages;
    }
}
