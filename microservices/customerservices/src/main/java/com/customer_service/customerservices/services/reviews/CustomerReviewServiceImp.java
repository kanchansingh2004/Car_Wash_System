package com.customer_service.customerservices.services.reviews;

import com.customer_service.customerservices.dto.CustomerReviewDTO;
import com.customer_service.customerservices.entity.CustomerReviewEntity;
import com.customer_service.customerservices.repository.CustomerReviewRepo;
import com.customer_service.customerservices.util.WasherClientCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CustomerReviewServiceImp implements CustomerReviewService {

    private static final Logger log = LoggerFactory.getLogger(CustomerReviewServiceImp.class);

    private final CustomerReviewRepo reviewRepository;
    private final WasherClientCall washerClientCall;

    public CustomerReviewServiceImp(CustomerReviewRepo reviewRepository, WasherClientCall washerClientCall) {
        this.reviewRepository = reviewRepository;
        this.washerClientCall = washerClientCall;
    }

    @Override
    public CustomerReviewDTO addReview(CustomerReviewDTO dto) {
        log.info("Adding new review by customer ID: {} for washer ID: {}", dto.getCustomerId(), dto.getWasherId());

        CustomerReviewEntity review = new CustomerReviewEntity();
        review.setWasherId(dto.getWasherId());
        review.setCustomerId(dto.getCustomerId());
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());
        review.setCreatedAt(LocalDateTime.now());

        CustomerReviewEntity saved = reviewRepository.save(review);
        log.info("Review saved successfully for washer ID: {}", saved.getWasherId());

        CustomerReviewDTO response = new CustomerReviewDTO();
        response.setWasherId(saved.getWasherId());
        response.setCustomerId(saved.getCustomerId());
        response.setComment(saved.getComment());
        response.setRating(saved.getRating());

        return response;
    }

    @Override
    public List<CustomerReviewDTO> getReviewsForCustomer(String customerId) {
        log.info("Fetching all reviews for customer ID: {}", customerId);
        return reviewRepository.findByCustomerId(customerId)
                .stream()
                .map(r -> new CustomerReviewDTO(r.getWasherId(), r.getCustomerId(), r.getComment(), r.getRating()))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> postReview(CustomerReviewDTO reviewDTO) {
        log.info("Posting review to washer service for washer ID: {}", reviewDTO.getWasherId());
        washerClientCall.postReviewToWasherService(reviewDTO);
        log.info("Review posted to washer service successfully for washer ID: {}", reviewDTO.getWasherId());
        return Map.of("Message", "Review Posted to washer successfully");
    }
}