package com.customer_service.customerservices;

import com.customer_service.customerservices.dto.BookingDTO;
import com.customer_service.customerservices.repository.BookingRepo;
import com.customer_service.customerservices.services.booking.BookingServiceImp;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootTest
@Transactional
public class BookingServiceTest {
    @Autowired
    private BookingServiceImp bookingService;

    @Autowired
    private BookingRepo bookingRepository;

    @Test
    public void testSaveBooking() {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setCustomerName("John Doe");
        bookingDTO.setCustomerEmail("john@example.com");
        bookingDTO.setWashPackage("Premium");
        bookingDTO.setWashDate(LocalDate.now());
        bookingDTO.setWashTime(LocalTime.now());
        bookingDTO.setBookingTime(LocalDateTime.now());
        bookingDTO.setBookingStatus("PENDING");

        BookingDTO result = bookingService.bookNow(bookingDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("John Doe", result.getCustomerName());
    }
}
