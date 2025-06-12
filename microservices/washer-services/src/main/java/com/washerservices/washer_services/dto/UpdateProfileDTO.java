package com.washerservices.washer_services.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileDTO {

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^[A-Z][a-z]{1,}$", message = "First name must start with a capital letter followed by at least one lowercase letter")
    private String firstName;

    @Pattern(regexp = "^[A-Z][a-z]{1,}$", message = "Last name must start with a capital letter followed by at least one lowercase letter")
    private String lastName;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phone;

    private String address;

    private String profileImage;
}
