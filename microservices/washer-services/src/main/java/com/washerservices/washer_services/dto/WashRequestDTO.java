package com.washerservices.washer_services.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WashRequestDTO {
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
    private String bookingStatus; // e.g. PENDING, ACCEPTED, DECLINED, IN_PROGRESS, COMPLETED
}
