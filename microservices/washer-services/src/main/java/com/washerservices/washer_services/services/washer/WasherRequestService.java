package com.washerservices.washer_services.services.washer;

import com.washerservices.washer_services.dto.BookingDTO;
import com.washerservices.washer_services.dto.CarDetailsDTO;
import com.washerservices.washer_services.dto.WasherBookingPayloadDTO;
import org.springframework.stereotype.Service;

@Service
public class WasherRequestService {
    public void processNewWashRequest(WasherBookingPayloadDTO payload) {
        BookingDTO booking = payload.getBooking();
        CarDetailsDTO car = payload.getCar();
        System.out.println("Received booking for: " + booking.getCustomerName());
    }
}

