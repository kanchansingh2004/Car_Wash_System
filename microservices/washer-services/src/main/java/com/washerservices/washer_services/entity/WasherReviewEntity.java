package com.washerservices.washer_services.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "washer_review_details")
public class WasherReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String washerId;
    private String customerId;
    private String comment;
    private int rating;

    private LocalDateTime createdAt;
}
