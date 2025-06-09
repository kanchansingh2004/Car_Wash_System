package com.washerservices.washer_services.services.washer;

import com.washerservices.washer_services.dto.UpdateProfileDTO;
import com.washerservices.washer_services.dto.WasherDTO;

import java.util.List;
import java.util.Optional;

public interface WasherService {
    List<WasherDTO> getAllWasher();
    WasherDTO addWasher(String email, String profileImage);
    UpdateProfileDTO updateWasher(String id, UpdateProfileDTO userDTO);
    void deleteWasher(String id);
    Optional<WasherDTO> getWasherById(String id);
}
