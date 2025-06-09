package com.customer_service.customerservices.services.booking;

import com.customer_service.customerservices.dto.BookingDTO;

import java.util.List;
import java.util.Map;

public interface BookingService {

    // Booking creation using passed userId (not from DTO)
    BookingDTO bookNow(BookingDTO dto, String userId, String carId, String washerId);

    List<BookingDTO> getAllBookings();

    BookingDTO getBookingById(Long bookingId);

    BookingDTO updateBooking(Long bookingId, BookingDTO dto);

    Map<String, String> cancelBooking(String bookingId);

    Map<String, List<BookingDTO>> getMyOrders(String customerId);
}
