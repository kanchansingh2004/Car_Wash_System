package com.washerservices.washer_services.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "pending_requests")
public class WashRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private String bookingStatus;
}
