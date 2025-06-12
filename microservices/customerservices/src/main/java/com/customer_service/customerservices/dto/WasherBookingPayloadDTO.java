package com.customer_service.customerservices.dto;

public class WasherBookingPayloadDTO {
    private BookingDTO booking;
    private CarDetailsDTO car;

    public WasherBookingPayloadDTO(BookingDTO booking, CarDetailsDTO car) {
        this.booking = booking;
        this.car = car;
    }
}
