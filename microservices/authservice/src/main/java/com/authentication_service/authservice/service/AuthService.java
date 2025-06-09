package com.authentication_service.authservice.service;

import com.authentication_service.authservice.dto.AuthProfileDTO;
import com.authentication_service.authservice.dto.AuthUserDTO;
import com.authentication_service.authservice.dto.SignupRequest;
import com.authentication_service.authservice.entity.AuthUserEntity;
import com.authentication_service.authservice.repository.AuthUserRepo;
import com.authentication_service.authservice.util.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {
    @Autowired private AuthUserRepo userRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JWTUtil jwtUtil;


    //Generate a unique id to the user (Customer, Admin, Washer)
    public String generateId(String role){
        switch (role){
            case "CUSTOMER":
                return "CUST_" + UUID.randomUUID().toString().substring(0, 8);
            case "WASHER":
                return "WASH_" + UUID.randomUUID().toString().substring(0, 8);
            case "ADMIN":
                return "ADMIN_" + UUID.randomUUID().toString().substring(0, 8);
        }

        return null;
    }

    //Register new User
    public String registerUser(SignupRequest userDTO){
        if(userRepo.findByEmail(userDTO.getEmail()).isPresent()){
            return "Email already exists!";
        }

        AuthUserEntity userEntity = new AuthUserEntity();

        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setRole(userDTO.getRole());
        userEntity.setAddress(userDTO.getAddress());
        userEntity.setPhone(userDTO.getPhone());

        String userId = generateId(userDTO.getRole());
        if(userId.isEmpty()){
            return "Not a valid user!!";
        }

        userEntity.setUserId(userId);
        System.out.println("The user ID is: " + userId + " for email: " + userDTO.getEmail());

        userRepo.save(userEntity);
        return "User Registered Successfully";
    }


    public Map<String, Object> login(AuthUserDTO userDTO) {
        AuthUserEntity user = userRepo.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return Map.of(
                "accessToken", jwtUtil.generateAccessToken(user.getEmail(), user.getRole()),
                "refreshToken", jwtUtil.generateRefreshToken(user.getEmail()),
                "expiresIn", TimeUnit.MILLISECONDS.toSeconds(JWTUtil.ACCESS_TOKEN_EXPIRE_TIME),
                "user", Map.of(
                        "message", "Login successful",
                        "email", user.getEmail(),
                        "role", user.getRole(),
                        "firstName",user.getFirstName(),
                        "lastName", user.getLastName(),
                        "User ID", user.getUserId()
                )
        );
    }

    public Map<String, String> refreshTokens(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        if (!"refresh".equals(jwtUtil.getTokenType(refreshToken))) {
            throw new RuntimeException("Not a refresh token");
        }

        String email = jwtUtil.extractClaim(refreshToken, Claims::getSubject);
        AuthUserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return Map.of(
                "accessToken", jwtUtil.generateAccessToken(email, user.getRole()),
                "refreshToken", jwtUtil.generateRefreshToken(email)
        );
    }


    //Reset password
    public Map<String, String> resetPassword(AuthUserDTO dto) {
        System.out.println("Looking for email: " + dto.getEmail());
        Optional<AuthUserEntity> optionalUser = userRepo.findByEmail(dto.getEmail().trim());

        if (optionalUser.isEmpty()) {
            return Map.of("Error", "Email does not exist!");
        }

        AuthUserEntity entity = optionalUser.get();
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Save the updated entity
        userRepo.save(entity);

        return Map.of("Message", "Password reset successfully");
    }

    //Get User Profile Using Email
    public AuthProfileDTO getCustomerProfile(String email){
        Optional<AuthUserEntity> optionalUser = userRepo.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return null;
        }
        AuthUserEntity entity = optionalUser.get();

        // Only allow customers
        if (!"CUSTOMER".equalsIgnoreCase(entity.getRole())) {
            return null;
        }

        AuthProfileDTO dto = new AuthProfileDTO();
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setRole(entity.getRole());
        dto.setEmail(entity.getEmail());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setUserId(entity.getUserId());

        return dto;
    }

    //Get User Profile Using Email
    public AuthProfileDTO getWasherProfile(String email){
        Optional<AuthUserEntity> optionalUser = userRepo.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return null;
        }
        AuthUserEntity entity = optionalUser.get();

        // Only allow customers
        if (!"WASHER".equalsIgnoreCase(entity.getRole())) {
            return null;
        }

        AuthProfileDTO dto = new AuthProfileDTO();
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setRole(entity.getRole());
        dto.setEmail(entity.getEmail());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setUserId(entity.getUserId());

        return dto;
    }

    //Get User Profile Using Email
    public AuthProfileDTO getAdminProfile(String email){
        Optional<AuthUserEntity> optionalUser = userRepo.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return null;
        }
        AuthUserEntity entity = optionalUser.get();

        // Only allow customers
        if (!"ADMIN".equalsIgnoreCase(entity.getRole())) {
            return null;
        }

        AuthProfileDTO dto = new AuthProfileDTO();
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setRole(entity.getRole());
        dto.setEmail(entity.getEmail());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setUserId(entity.getUserId());

        return dto;
    }
}