package com.washerservices.washer_services.services.washer;

import com.customer_service.customerservices.dto.BookingDTO;
import com.washerservices.washer_services.dto.UpdateProfileDTO;
import com.washerservices.washer_services.dto.WashRequestDTO;
import com.washerservices.washer_services.dto.WasherDTO;
import com.washerservices.washer_services.entity.WashRequestEntity;
import com.washerservices.washer_services.entity.WasherEntity;
import com.washerservices.washer_services.exceptionhandling.AlreadyPresentException;
import com.washerservices.washer_services.exceptionhandling.NotFoundException;
import com.washerservices.washer_services.repository.PendingRequestRepository;
import com.washerservices.washer_services.repository.WasherRepository;
import com.washerservices.washer_services.util.AuthClientCall;
import com.washerservices.washer_services.util.BookingClientCall;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
@Service
public class WasherServicesImp implements WasherService {
    @Autowired private WasherRepository washerRepo;
    @Autowired private AuthClientCall client;
    @Autowired private ModelMapper modelMapper;
    @Autowired private BookingClientCall bookingClientCall;
    @Autowired private PendingRequestRepository pendingRequestRepo;

    private static final Logger log = LoggerFactory.getLogger(WasherServicesImp.class);

    public Map<String, List<BookingDTO>> getWasherBookings(String washerId) {
        log.info("Fetching all bookings for washerId: {}", washerId);
        return bookingClientCall.getBookingsForWasher(washerId);
    }

    @Override
    public List<WasherDTO> getAllWasher() {
        log.info("Fetching all washers from DB");
        List<WasherEntity> users = washerRepo.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, WasherDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public WasherDTO addWasher(String email, String profileImage) throws AlreadyPresentException, NotFoundException {
        log.info("Adding washer with email: {}", email);

        WasherDTO authProfile = client.getWasherProfile(email);
        if (authProfile == null) {
            log.warn("Washer not found in Auth Service for email: {}", email);
            throw new NotFoundException("Washer not found");
        }

        if(washerRepo.findByEmail(email).isPresent()){
            log.warn("Washer already registered with email: {}", email);
            throw new AlreadyPresentException("Washer is already registered!!");
        }

        WasherDTO washerDTO = new WasherDTO();
        washerDTO.setEmail(authProfile.getEmail());
        washerDTO.setFirstName(authProfile.getFirstName());
        washerDTO.setLastName(authProfile.getLastName());
        washerDTO.setProfileImage(profileImage);
        washerDTO.setAddress(authProfile.getAddress());
        washerDTO.setPhone(authProfile.getPhone());
        washerDTO.setRole(authProfile.getRole());
        washerDTO.setUserId(authProfile.getUserId());

        TypeMap<WasherDTO, WasherEntity> typeMap = modelMapper.typeMap(WasherDTO.class, WasherEntity.class);
        typeMap.addMappings(mapper -> {
            mapper.skip(WasherEntity::setId);
            mapper.map(WasherDTO::getUserId, WasherEntity::setUserId);
        });

        WasherEntity entity = modelMapper.map(washerDTO, WasherEntity.class);
        entity.setProfileImage(profileImage);
        washerRepo.save(entity);

        log.info("Washer added successfully: {}", washerDTO.getEmail());
        return washerDTO;
    }

    @Override
    public UpdateProfileDTO updateWasher(String id, UpdateProfileDTO userDTO) {
        log.info("Updating washer with ID: {}", id);
        return washerRepo.findByUserId(id).map(entity -> {
            entity.setFirstName(userDTO.getFirstName());
            entity.setLastName(userDTO.getLastName());
            entity.setPhone(userDTO.getPhone());
            entity.setAddress(userDTO.getAddress());
            entity.setEmail(userDTO.getEmail());
            entity.setProfileImage(userDTO.getProfileImage());
            WasherEntity updatedEntity = washerRepo.save(entity);
            log.info("Washer updated for ID: {}", id);
            return modelMapper.map(updatedEntity, UpdateProfileDTO.class);
        }).orElse(null);
    }

    @Override
    public void deleteWasher(String id){
        log.info("Attempting to delete washer with ID: {}", id);
        if(washerRepo.findByUserId(id).isEmpty()){
            log.warn("Washer with ID {} not found!", id);
            throw new NotFoundException("Washer with ID " + id + " Not exists");
        }
        WasherEntity dto = washerRepo.findByUserId(id).get();
        washerRepo.deleteByUserId(dto.getUserId());
        log.info("Washer deleted with ID: {}", id);
    }

    @Override
    public Optional<WasherDTO> getWasherById(String id) {
        log.info("Fetching washer by ID: {}", id);
        Optional<WasherEntity> user = washerRepo.findByUserId(id);
        if(user.isEmpty()){
            log.warn("Washer not found with ID: {}", id);
            throw new NotFoundException("Washer with ID " + id + " Not found!!");
        }
        return user.map(u -> modelMapper.map(u, WasherDTO.class));
    }

    public List<WashRequestDTO> getPendingRequestsForWasher(String washerId) {
        log.info("Fetching pending requests for washerId: {}", washerId);
        List<WashRequestEntity> list = pendingRequestRepo.findAllByWasherId(washerId);
        return list.stream().map(x -> modelMapper.map(x, WashRequestDTO.class)).toList();
    }

    public String updateRequestStatus(String bookingId, String newStatus) {
        log.info("Updating booking status for bookingId: {} to {}", bookingId, newStatus);
        WashRequestEntity request = pendingRequestRepo.findByUserBookingId(bookingId);
        if (request == null) {
            log.warn("Booking not found with ID: {}", bookingId);
            throw new NotFoundException("Booking not found");
        }

        request.setBookingStatus(newStatus);
        String response = bookingClientCall.updateBookingStatus(bookingId, newStatus);
        pendingRequestRepo.delete(request);
        log.info("Booking status updated and request deleted for bookingId: {}", bookingId);
        return "Booking status updated locally and sent to Customer Service. Response: " + response;
    }

    public void receiveNewRequest(WashRequestDTO dto) {
        dto.setBookingStatus("PENDING");
        pendingRequestRepo.save(modelMapper.map(dto, WashRequestEntity.class));
        log.info("Received new request for washerId: {}", dto.getWasherId());
    }

    public void savePendingBooking(BookingDTO bookingDTO) {
        WashRequestEntity bookingEntity = new WashRequestEntity();
        bookingEntity.setUserBookingId(bookingDTO.getUserBookingId());
        bookingEntity.setWasherId(bookingDTO.getWasherId());
        bookingEntity.setCustomerName(bookingDTO.getCustomerName());
        bookingEntity.setCustomerEmail(bookingDTO.getCustomerEmail());
        bookingEntity.setWashAddress(bookingDTO.getWashAddress());
        bookingEntity.setAddOns(bookingDTO.getAddOns());
        bookingEntity.setNotes(bookingDTO.getNotes());
        bookingEntity.setWashPackage(bookingDTO.getWashPackage());
        bookingEntity.setWashDate(bookingDTO.getWashDate());
        bookingEntity.setWashTime(bookingDTO.getWashTime());
        bookingEntity.setBookingStatus(bookingDTO.getBookingStatus());

        pendingRequestRepo.save(bookingEntity);
        log.info("Saved new pending booking for washerId: {}", bookingEntity.getWasherId());
    }
}