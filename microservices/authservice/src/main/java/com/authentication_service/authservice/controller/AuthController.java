package com.authentication_service.authservice.controller;

import com.authentication_service.authservice.dto.*;
import com.authentication_service.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/request/auth")
public class AuthController {
    @Autowired private AuthService authService;

    //Register new user
    @PostMapping("/signup")
    public Map<String, String> registerUser(@RequestBody SignupRequest userDTO){
        return Map.of("Message", authService.registerUser(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthUserDTO userDTO) {
        try {
            return ResponseEntity.ok(authService.login(userDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        try {
            return ResponseEntity.ok(authService.refreshTokens(request.get("refreshToken")));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    //Reset password
    @PutMapping("/resetPassword")
    public Map<String,String> resetPassword(@RequestBody AuthUserDTO dto){
        return authService.resetPassword(dto);
    }

    //Get User Profile Using Email
    @GetMapping("/customer/getProfile")
    public ResponseEntity<?> getCustomerProfile(@RequestParam String email) {
        AuthProfileDTO profile = authService.getCustomerProfile(email);

        if (profile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Customer not found or not a valid customer"));
        }

        return ResponseEntity.ok(profile);
    }

    //Get User Profile Using Email
    @GetMapping("/washer/getProfile")
    public ResponseEntity<?> getWasherProfile(@RequestParam String email) {
        AuthProfileDTO profile = authService.getWasherProfile(email);

//        if (profile == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Washer not found or not a valid washer");
//        }

        return ResponseEntity.ok(profile);
    }

    //Get User Profile Using Email
    @GetMapping("/admin/getProfile")
    public ResponseEntity<?> getAdminProfile(@RequestParam String email) {
        AuthProfileDTO profile = authService.getAdminProfile(email);

        if (profile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Admin not found or not a valid admin"));
        }

        return ResponseEntity.ok(profile);
    }
}