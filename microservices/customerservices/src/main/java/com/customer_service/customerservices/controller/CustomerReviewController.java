package com.customer_service.customerservices.controller;

import com.customer_service.customerservices.dto.CustomerReviewDTO;
import com.customer_service.customerservices.services.reviews.CustomerReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer/review")
public class CustomerReviewController {

    private static final Logger log = LoggerFactory.getLogger(CustomerReviewController.class);

    private final CustomerReviewService reviewService;

    public CustomerReviewController(CustomerReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Review to Washer Service
    @PostMapping("/postReview")
    public ResponseEntity<Map<String, String>> postReview(@RequestBody CustomerReviewDTO reviewDTO) {
        log.info("Received review for washer ID: {}", reviewDTO.getWasherId());
        return ResponseEntity.ok(reviewService.postReview(reviewDTO));
    }

    // Add Review in Customer DB
    @PostMapping("/addReview")
    public ResponseEntity<CustomerReviewDTO> addReview(@RequestBody CustomerReviewDTO reviewDTO) {
        log.info("Adding review by customer ID: {} for washer ID: {}", reviewDTO.getCustomerId(), reviewDTO.getWasherId());
        return ResponseEntity.status(201).body(reviewService.addReview(reviewDTO));
    }

    // Get Reviews from Customer DB
    @GetMapping("/getReview/{customerId}")
    public ResponseEntity<List<CustomerReviewDTO>> getReviews(@PathVariable String customerId) {
        log.info("Fetching reviews for customer ID: {}", customerId);
        return ResponseEntity.ok(reviewService.getReviewsForCustomer(customerId));
    }
}