package com.customer_service.customerservices.dto;

import lombok.Data;

@Data
public class CustomerReviewDTO {
    private String washerId;
    private String customerId;
    private String comment;
    private int rating;

    public CustomerReviewDTO(String washerId,String customerId, String comment, int rating) {
        this.washerId = washerId;
        this.customerId=customerId;
        this.comment = comment;
        this.rating = rating;
    }

    public CustomerReviewDTO(){

    }

    public String getWasherId() {
        return washerId;
    }

    public void setWasherId(String washerId) {
        this.washerId = washerId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
