package com.washerservices.washer_services.services.review;

import com.washerservices.washer_services.dto.WasherReviewDTO;
import com.washerservices.washer_services.entity.WasherReviewEntity;
import com.washerservices.washer_services.repository.WasherReviewRepository;
import com.washerservices.washer_services.util.BookingClientCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WasherReviewServiceImp implements WasherReviewService {

    private static final Logger log = LoggerFactory.getLogger(WasherReviewServiceImp.class);

    @Autowired
    private WasherReviewRepository reviewRepository;
    @Autowired
    private BookingClientCall bookingClientCall;

    @Override
    public WasherReviewDTO addReview(WasherReviewDTO dto) {
        WasherReviewEntity review = new WasherReviewEntity();
        review.setWasherId(dto.getWasherId());
        review.setCustomerId(dto.getCustomerId());
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());
        review.setCreatedAt(LocalDateTime.now());

        WasherReviewEntity saved = reviewRepository.save(review);
        log.info("Saved review for washerId: {}", saved.getWasherId());

        WasherReviewDTO response = new WasherReviewDTO();
        response.setWasherId(saved.getWasherId());
        response.setCustomerId(saved.getCustomerId());
        response.setComment(saved.getComment());
        response.setRating(saved.getRating());

        return response;
    }

    @Override
    public List<WasherReviewDTO> getReviewsForWasher(String washerId) {
        log.info("Retrieving all reviews for washerId: {}", washerId);
        return reviewRepository.findByWasherId(washerId)
                .stream()
                .map(r -> new WasherReviewDTO(r.getWasherId(), r.getCustomerId(), r.getComment(), r.getRating()))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String,String> postReview(WasherReviewDTO reviewDTO) {
        log.info("Posting review to customer service for washerId: {}", reviewDTO.getWasherId());
        bookingClientCall.postReviewToCustomerService(reviewDTO);
        return Map.of("Message", "Review Posted to customer successfully");
    }
}
