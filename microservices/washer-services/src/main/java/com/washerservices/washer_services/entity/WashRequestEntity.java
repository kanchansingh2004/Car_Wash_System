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

    @Column(nullable = false)
    private String userBookingId;

    @Column(nullable = false)
    private String washerId;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String customerEmail;

    @Column(nullable = false)
    private String washPackage;

    private String addOns;

    private String notes;

    @Column(nullable = false)
    private String washAddress;

    @Column(nullable = false)
    private LocalDate washDate;

    @Column(nullable = false)
    private LocalTime washTime;

    @Column(nullable = false)
    private String bookingStatus;
}
