package com.customer_service.customerservices.util;

import com.customer_service.customerservices.dto.BookingDTO;
import com.customer_service.customerservices.dto.CustomerReviewDTO;
import com.customer_service.customerservices.dto.WasherBookingPayloadDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "washer-services", url = "http://localhost:8060")
public interface WasherClientCall {
    @PostMapping("/request/washer/wash-request/new")
    void sendBookingToWasherService(@RequestBody BookingDTO bookingDTO);

    @PostMapping("/request/washer/wash-request")
    void sendWashRequest(WasherBookingPayloadDTO payload);

    @PostMapping("request/washer/reviews/addReview")
    void postReviewToWasherService(@RequestBody CustomerReviewDTO customerReviewDTO);

}
