package com.washerservices.washer_services.services.review;

import com.washerservices.washer_services.dto.WasherReviewDTO;

import java.util.List;
import java.util.Map;

public interface WasherReviewService {
    WasherReviewDTO addReview(WasherReviewDTO reviewDTO);
    List<WasherReviewDTO> getReviewsForWasher(String washerId);
    Map<String,String> postReview(WasherReviewDTO reviewDTO);
}
