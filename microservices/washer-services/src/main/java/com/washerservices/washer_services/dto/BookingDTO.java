package com.washerservices.washer_services.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class BookingDTO {

    private String userBookingId;
    private String washerId;
    private String customerName;
    private String customerEmail;
    private String washPackage;
    private String addOns;
    private String notes;
    private String washAddress;
    private LocalDate washDate;
    private LocalTime washTime;
    private LocalDateTime bookingTime;
    private String bookingStatus;

    // ===== Getters and Setters =====

    public String getWasherId() {
        return washerId;
    }

    public void setWasherId(String washerId) {
        this.washerId = washerId;
    }

    public String getUserBookingId() {
        return userBookingId;
    }

    public void setUserBookingId(String userBookingId) {
        this.userBookingId = userBookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getWashPackage() {
        return washPackage;
    }

    public void setWashPackage(String washPackage) {
        this.washPackage = washPackage;
    }

    public String getAddOns() {
        return addOns;
    }

    public void setAddOns(String addOns) {
        this.addOns = addOns;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getWashAddress() {
        return washAddress;
    }

    public void setWashAddress(String washAddress) {
        this.washAddress = washAddress;
    }

    public LocalDate getWashDate() {
        return washDate;
    }

    public void setWashDate(LocalDate washDate) {
        this.washDate = washDate;
    }

    public LocalTime getWashTime() {
        return washTime;
    }

    public void setWashTime(LocalTime washTime) {
        this.washTime = washTime;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
