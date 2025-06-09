package com.washerservices.washer_services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String profileImage;
}
