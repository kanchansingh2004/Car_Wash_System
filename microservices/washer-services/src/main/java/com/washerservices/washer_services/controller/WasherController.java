package com.washerservices.washer_services.controller;

import com.customer_service.customerservices.dto.BookingDTO;
import com.washerservices.washer_services.dto.UpdateProfileDTO;
import com.washerservices.washer_services.dto.WashRequestDTO;
import com.washerservices.washer_services.dto.WasherDTO;
import com.washerservices.washer_services.exceptionhandling.NotFoundException;
import com.washerservices.washer_services.services.washer.WasherServicesImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/request/washer")
public class WasherController {

    @Autowired
    private WasherServicesImp washerServices;

    private static final Logger log = LoggerFactory.getLogger(WasherController.class);

    //Random
    @GetMapping("/rand")
    public String hello(){
        log.info("Health check endpoint '/rand' hit.");
        return "Hello";
    }

    //Add user
    @PostMapping("/post")
    public ResponseEntity<WasherDTO> addUser(@RequestParam String email, @RequestBody Map<String,String> requestBody){
        log.info("Received request to add new washer with email: {}", email);
        String profileImage = requestBody.get("profileImage");
        WasherDTO savedUser = washerServices.addWasher(email, profileImage);
        log.info("Successfully added washer with email: {}", email);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    //Get all users
    @GetMapping("/get")
    public ResponseEntity<List<WasherDTO>> getAllUsers(){
        log.info("Fetching all washer profiles.");
        List<WasherDTO> users = washerServices.getAllWasher();
        log.info("Total washers fetched: {}", users.size());
        return ResponseEntity.ok(users);
    }

    //Get user by id
    @GetMapping("/getBy/{id}")
    public ResponseEntity<WasherDTO> getUserById(@PathVariable String id){
        log.info("Fetching washer details for ID: {}", id);
        WasherDTO userDTO = washerServices.getWasherById(id).orElseThrow(
                () -> {
                    log.warn("Washer not found with ID: {}", id);
                    return new NotFoundException("User with ID "+ id + " Not found!!");
                }
        );
        log.info("Successfully fetched washer details for ID: {}", id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    //Delete washer by ID
    @DeleteMapping("/deleteBy/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        log.info("Received request to delete washer with ID: {}", id);
        washerServices.deleteWasher(id);
        log.info("Successfully deleted washer with ID: {}", id);
        return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
    }

    //Update washer profile
    @PutMapping("/update/{id}")
    public ResponseEntity<UpdateProfileDTO> updateCustomer(@PathVariable String id, @RequestBody UpdateProfileDTO userDTO){
        log.info("Received update request for washer ID: {}", id);
        UpdateProfileDTO updated = washerServices.updateWasher(id, userDTO);
        log.info("Successfully updated washer profile for ID: {}", id);
        return ResponseEntity.ok(updated);
    }

    //Get bookings for a washer
    @GetMapping("/my-orders/{washerId}")
    public ResponseEntity<Map<String, List<BookingDTO>>> getWasherOrders(@PathVariable String washerId) {
        log.info("Fetching bookings for washer ID: {}", washerId);
        Map<String, List<BookingDTO>> bookings = washerServices.getWasherBookings(washerId);
        log.info("Fetched {} bookings for washer ID: {}", bookings.values().stream().mapToInt(List::size).sum(), washerId);
        return ResponseEntity.ok(bookings);
    }

    //Simulate receiving a wash request (manually)
    @PostMapping("/wash-request/receive")
    public ResponseEntity<String> receiveWashRequest(@RequestBody WashRequestDTO dto) {
        log.info("Received new wash request for washer ID: {}", dto.getWasherId());
        washerServices.receiveNewRequest(dto);
        log.info("Saved new wash request for washer ID: {}", dto.getWasherId());
        return ResponseEntity.ok("Wash request received!");
    }

    //Get pending requests for a washer
    @GetMapping("/wash-request/pending/{washerId}")
    public ResponseEntity<List<WashRequestDTO>> getPendingRequests(@PathVariable String washerId) {
        log.info("Fetching pending wash requests for washer ID: {}", washerId);
        List<WashRequestDTO> pendingRequests = washerServices.getPendingRequestsForWasher(washerId);
        log.info("Found {} pending requests for washer ID: {}", pendingRequests.size(), washerId);
        return ResponseEntity.ok(pendingRequests);
    }

    //Accept or decline a wash request
    @PutMapping("/wash-request/update-status")
    public ResponseEntity<String> updateRequestStatus(@RequestParam String bookingId,
                                                      @RequestParam String status) {
        log.info("Updating status for booking ID: {} to '{}'", bookingId, status);
        String response = washerServices.updateRequestStatus(bookingId, status);
        log.info("Status updated for booking ID: {}", bookingId);
        return ResponseEntity.ok(response);
    }

    //Save a new pending booking (from Booking Service)
    @PostMapping("/wash-request/new")
    public ResponseEntity<String> receiveBooking(@RequestBody BookingDTO bookingDTO) {
        log.info("Received booking from Booking Service for washer ID: {}", bookingDTO.getWasherId());
        washerServices.savePendingBooking(bookingDTO);
        log.info("Saved booking with ID: {} for washer ID: {}", bookingDTO.getUserBookingId(), bookingDTO.getWasherId());
        return ResponseEntity.ok("Received");
    }
}
