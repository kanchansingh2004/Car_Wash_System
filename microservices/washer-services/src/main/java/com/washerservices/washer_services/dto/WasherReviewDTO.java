package com.washerservices.washer_services.dto;

import lombok.*;

@Data
public class WasherReviewDTO {
    private String washerId;
    private String customerId;
    private String comment;
    private int rating;

    public WasherReviewDTO(String washerId, String customerId, String comment, int rating) {
        this.washerId = washerId;
        this.customerId = customerId;
        this.comment = comment;
        this.rating = rating;
    }

    public WasherReviewDTO() {

    }
}
