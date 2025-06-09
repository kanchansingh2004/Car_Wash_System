package com.customer_service.customerservices.controller;

import com.customer_service.customerservices.dto.BookingDTO;
import com.customer_service.customerservices.services.booking.BookingServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/request/customer/booking")
public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    BookingServiceImp bookingService;

    @PostMapping("/bookNow")
    public ResponseEntity<BookingDTO> bookNow(
            @RequestParam String userId,
            @RequestBody BookingDTO dto,
            @RequestParam String carId,
            @RequestParam String washerId
    ) {
        logger.info("Booking requested by user: {}", userId);
        BookingDTO response = bookingService.bookNow(dto,userId,carId,washerId);
        logger.info("Booking successful for user: {}, Booking ID: {}", userId, response.getUserBookingId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        logger.info("Fetching all bookings");
        List<BookingDTO> bookings = bookingService.getAllBookings();
        logger.info("Total bookings fetched: {}", bookings.size());
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long bookingId) {
        logger.info("Fetching booking with ID: {}", bookingId);
        BookingDTO booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(booking);
    }

    @PutMapping("/update/{bookingId}")
    public ResponseEntity<BookingDTO> updateBooking(
            @PathVariable Long bookingId,
            @RequestBody BookingDTO dto
    ) {
        logger.info("Updating booking with ID: {}", bookingId);
        BookingDTO updated = bookingService.updateBooking(bookingId, dto);
        logger.info("Updated booking ID: {}", updated.getUserBookingId());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<Map<String, String>> cancelBooking(@PathVariable String bookingId) {
        logger.info("Cancelling booking with ID: {}", bookingId);
        Map<String, String> result = bookingService.cancelBooking(bookingId);
        logger.info("Booking ID {} cancellation status: {}", bookingId, result.get("status"));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/my-orders/{userId}")
    public ResponseEntity<Map<String, List<BookingDTO>>> getMyOrders(@PathVariable String userId) {
        logger.info("Fetching orders for userId: {}", userId);
        Map<String, List<BookingDTO>> orders = bookingService.getMyOrders(userId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/update-status")
    public ResponseEntity<String> updateBookingStatus(@RequestParam String bookingId,
                                                      @RequestParam String status) {
        bookingService.updateBookingStatus(bookingId, status);
        return ResponseEntity.ok("Booking status updated in Customer Service!");
    }

}
