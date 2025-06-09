package com.customer_service.customerservices.services.booking;

import com.customer_service.customerservices.dto.BookingDTO;
import com.customer_service.customerservices.dto.CarDetailsDTO;
import com.customer_service.customerservices.dto.WasherBookingPayloadDTO;
import com.customer_service.customerservices.entity.BookingEntity;
import com.customer_service.customerservices.entity.CarDetails;
import com.customer_service.customerservices.exceptionhandling.NotFoundException;
import com.customer_service.customerservices.repository.BookingRepo;
import com.customer_service.customerservices.repository.CarRepo;
import com.customer_service.customerservices.util.WasherClientCall;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImp implements BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImp.class);
    @Autowired private BookingRepo bookingRepo;
    @Autowired private ModelMapper modelMapper;
    @Autowired private WasherClientCall washerClientCall;
    @Autowired private CarRepo carRepo;

   //Create a new booking using details
    @Override
    public BookingDTO bookNow(BookingDTO dto, String userId, String carId, String washerId) {
        logger.info("Attempting to create booking for userId: {}", userId);

        BookingEntity booking = new BookingEntity();

        // Set fields from DTO
        booking.setCustomerName(dto.getCustomerName());
        booking.setCustomerEmail(dto.getCustomerEmail());
        booking.setWashPackage(dto.getWashPackage());
        booking.setAddOns(dto.getAddOns());
        booking.setNotes(dto.getNotes());
        booking.setWashAddress(dto.getWashAddress());
        booking.setWashDate(dto.getWashDate());
        booking.setWashTime(dto.getWashTime());
        booking.setUserBookingId(generateId());
        booking.setWasherId(washerId);

        // Set server-side controlled fields
        booking.setUserId(userId);
        booking.setCarId(carId);
        booking.setBookingTime(LocalDateTime.now());
        booking.setBookingStatus("PENDING");

        BookingEntity saved = bookingRepo.save(booking);
        logger.info("Booking created with ID: {} for userId: {}", saved.getBookingId(), userId);
        BookingDTO savedDTO = modelMapper.map(saved, BookingDTO.class);

        // notify Washer Service
        try {
            washerClientCall.sendBookingToWasherService(savedDTO);
        } catch (Exception e) {
            // handle failure or retry logic
            System.err.println("Failed to notify washer service: " + e.getMessage());
        }

        return modelMapper.map(saved, BookingDTO.class);
    }

    private String generateId() {
        return "BOOKID_" + UUID.randomUUID().toString().substring(0, 8);
    }


    //Get all bookings
    @Override
    public List<BookingDTO> getAllBookings() {
        List<BookingEntity> all = bookingRepo.findAll();
        return all.stream()
                .map(b -> modelMapper.map(b, BookingDTO.class))
                .collect(Collectors.toList());
    }

    //Get booking by booking ID
    @Override
    public BookingDTO getBookingById(Long bookingId) {
        BookingEntity booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> {
                    logger.error("Booking not found with ID: {}", bookingId);
                    return new NotFoundException("Booking not found with ID: " + bookingId);
                });

        return modelMapper.map(booking, BookingDTO.class);
    }

    //Update an existing booking
    @Override
    public BookingDTO updateBooking(Long bookingId, BookingDTO dto) {
        BookingEntity existing = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking not found with ID: " + bookingId));

        if ("Completed".equalsIgnoreCase(existing.getBookingStatus()) ||
                "Cancelled".equalsIgnoreCase(existing.getBookingStatus())) {
            throw new RuntimeException("Cannot update a completed or cancelled booking.");
        }

        // Update editable fields
        existing.setWashPackage(dto.getWashPackage());
        existing.setAddOns(dto.getAddOns());
        existing.setNotes(dto.getNotes());
        existing.setWashAddress(dto.getWashAddress());
        existing.setWashDate(dto.getWashDate());
        existing.setWashTime(dto.getWashTime());

        BookingEntity updated = bookingRepo.save(existing);
        logger.info("Booking ID {} updated successfully", bookingId);

        return modelMapper.map(updated, BookingDTO.class);
    }

    //Cancel a booking by ID
    @Override
    public Map<String, String> cancelBooking(String bookingId) {
        BookingEntity booking = bookingRepo.findByUserBookingId(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking not found with ID: " + bookingId));

        if ("Completed".equalsIgnoreCase(booking.getBookingStatus())) {
            throw new RuntimeException("Cannot cancel a completed booking.");
        }

        booking.setBookingStatus("Cancelled");
        bookingRepo.save(booking);
        logger.info("Booking ID {} has been cancelled", bookingId);

        return Map.of("message", "Booking cancelled successfully", "bookingId", bookingId.toString());
    }

    //Get all current and past orders for a user
    @Override
    public Map<String, List<BookingDTO>> getMyOrders(String customerId) {
        List<BookingEntity> allBookings = bookingRepo.findByUserId(customerId);

        List<BookingDTO> current = new ArrayList<>();
        List<BookingDTO> past = new ArrayList<>();

        for (BookingEntity booking : allBookings) {
            BookingDTO dto = modelMapper.map(booking, BookingDTO.class);
            if ("Completed".equalsIgnoreCase(booking.getBookingStatus()) ||
                    "Cancelled".equalsIgnoreCase(booking.getBookingStatus())) {
                past.add(dto);
            } else {
                current.add(dto);
            }
        }

        logger.info("Retrieved {} current and {} past orders for userId {}", current.size(), past.size(), customerId);

        return Map.of(
                "currentOrders", current,
                "pastOrders", past
        );
    }

    public void updateBookingStatus(String bookingId, String status) {
        BookingEntity booking = bookingRepo.findByUserBookingId(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking not found"));

        booking.setBookingStatus(status);
        bookingRepo.save(booking);
    }



    public void sendWashRequestToWasher(String bookingId) {
        BookingEntity booking = bookingRepo.findByUserBookingId(bookingId).orElseThrow();
        CarDetails car = carRepo.findByCarNumber(booking.getCarId()).orElseThrow();

        BookingDTO bookingDTO = modelMapper.map(booking, BookingDTO.class);  // method to map
        CarDetailsDTO carDTO = modelMapper.map(car, CarDetailsDTO.class);

        WasherBookingPayloadDTO payload = new WasherBookingPayloadDTO(bookingDTO, carDTO);

        washerClientCall.sendWashRequest(payload);
    }

}
