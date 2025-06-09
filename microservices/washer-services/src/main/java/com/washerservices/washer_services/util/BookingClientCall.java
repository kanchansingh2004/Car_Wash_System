package com.washerservices.washer_services.util;

import com.customer_service.customerservices.dto.BookingDTO;
import com.washerservices.washer_services.dto.WasherReviewDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Component
@FeignClient(name = "customer-services", url = "http://localhost:8020")
public interface BookingClientCall {
    @GetMapping("/request/customer/booking/my-orders/{userId}")
    Map<String, List<BookingDTO>> getBookingsForWasher(@PathVariable("userId") String userId);


    @PutMapping("/request/customer/booking/update-status")
    String updateBookingStatus(@RequestParam("bookingId") String bookingId,
                               @RequestParam("status") String status);


    @PostMapping("/request/customer/reviews/addReview")
    void postReviewToCustomerService(@RequestBody WasherReviewDTO washerReviewDTO);
}
