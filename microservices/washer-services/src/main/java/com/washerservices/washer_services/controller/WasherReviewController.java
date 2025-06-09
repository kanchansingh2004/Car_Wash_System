package com.washerservices.washer_services.controller;

import com.washerservices.washer_services.dto.WasherReviewDTO;
import com.washerservices.washer_services.services.review.WasherReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("request/washer/reviews")
public class WasherReviewController {

    private static final Logger log = LoggerFactory.getLogger(WasherReviewController.class);

    @Autowired
    private WasherReviewService reviewService;

    //Post review to customer
    @PostMapping("/postReview")
    public ResponseEntity<Map<String,String>> postReview(@RequestBody WasherReviewDTO reviewDTO){
        log.info("Posting review to customer for washerId: {}", reviewDTO.getWasherId());
        return ResponseEntity.ok(reviewService.postReview(reviewDTO));
    }

    // Post a new review
    @PostMapping("/addReview")
    public ResponseEntity<WasherReviewDTO> addReview(@RequestBody WasherReviewDTO reviewDTO) {
        log.info("Adding review for washerId: {}", reviewDTO.getWasherId());
        return new ResponseEntity<>(reviewService.addReview(reviewDTO), HttpStatus.CREATED);
    }

    // Get all reviews for a washer
    @GetMapping("/getReview/{washerId}")
    public ResponseEntity<List<WasherReviewDTO>> getReviews(@PathVariable String washerId) {
        log.info("Fetching reviews for washerId: {}", washerId);
        return ResponseEntity.ok(reviewService.getReviewsForWasher(washerId));
    }
}
