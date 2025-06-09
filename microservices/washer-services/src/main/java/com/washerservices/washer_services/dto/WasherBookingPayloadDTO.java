package com.washerservices.washer_services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WasherBookingPayloadDTO {
    private BookingDTO booking;
    private CarDetailsDTO car;
}

