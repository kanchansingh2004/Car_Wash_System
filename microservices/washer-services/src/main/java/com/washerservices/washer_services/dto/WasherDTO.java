package com.washerservices.washer_services.dto;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WasherDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String role;
    private String profileImage;
    private String userId;
}
