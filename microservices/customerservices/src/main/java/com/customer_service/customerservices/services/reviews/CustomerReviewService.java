package com.customer_service.customerservices.services.reviews;

import com.customer_service.customerservices.dto.CustomerReviewDTO;

import java.util.List;
import java.util.Map;

public interface CustomerReviewService {
    CustomerReviewDTO addReview(CustomerReviewDTO reviewDTO);
    List<CustomerReviewDTO> getReviewsForCustomer(String customerId);
    Map<String,String> postReview(CustomerReviewDTO reviewDTO);
}
