package com.customer_service.customerservices;

import com.customer_service.customerservices.dto.CustomerReviewDTO;
import com.customer_service.customerservices.repository.CustomerRepo;
import com.customer_service.customerservices.services.reviews.CustomerReviewServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerReviewServiceTest {
    @Autowired
    private CustomerReviewServiceImp customerReviewService;

    @Autowired
    private CustomerRepo customerReviewRepository;

    @Test
    public void testSaveReview() {
        CustomerReviewDTO reviewDTO = new CustomerReviewDTO();
        reviewDTO.setComment("Excellent Service");
        reviewDTO.setRating(5);

        CustomerReviewDTO result = customerReviewService.addReview(reviewDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Excellent Service", result.getComment());
    }
}
