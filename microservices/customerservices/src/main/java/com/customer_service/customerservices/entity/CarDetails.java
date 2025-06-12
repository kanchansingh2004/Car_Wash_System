package com.customer_service.customerservices.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "car_details")
public class CarDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String CarId;
    @Column(nullable = false, unique = true)
    private String carNumber;

    @Column(nullable = false)
    private String carModel;

    private String carImages;

    @Column(nullable = false)
    private String userId;


    public String getCarId() {
        return CarId;
    }

    public void setCarId(String carId) {
        CarId = carId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getCarImages() {
        return carImages;
    }

    public void setCarImages(String carImages) {
        this.carImages = carImages;
    }
}
