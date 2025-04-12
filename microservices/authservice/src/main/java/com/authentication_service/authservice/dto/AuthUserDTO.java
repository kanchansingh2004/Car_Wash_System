package com.authentication_service.authservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class AuthUserDTO {
    private String email;
    private String password;
    private String role;

    // Add getters and setters for all fields

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

}

