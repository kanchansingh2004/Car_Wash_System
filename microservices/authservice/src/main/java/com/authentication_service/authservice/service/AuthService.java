package com.authentication_service.authservice.service;

import com.authentication_service.authservice.dto.AuthUserDTO;
import com.authentication_service.authservice.dto.SignupRequest;
import com.authentication_service.authservice.entity.AuthUserEntity;
import com.authentication_service.authservice.repository.AuthUserRepo;
import com.authentication_service.authservice.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private AuthUserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

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

        userRepo.save(userEntity);
        return "User Registered Successfully";
    }

    //Login Existing user and return jwt token
    public Map<String,Object> login(AuthUserDTO userDTO){
        Optional<AuthUserEntity> optionalAuthUser = userRepo.findByEmail(userDTO.getEmail());

        if(optionalAuthUser.isEmpty()){
            return Map.of("Error", "Invalid email or password");
        }

        AuthUserEntity userEntity = optionalAuthUser.get();
        boolean isPasswordMatch = passwordEncoder.matches(userDTO.getPassword(), userEntity.getPassword());

        if(!isPasswordMatch){
            return Map.of("error", "Invalid email or password");
        }

        // Generate token
        String token = jwtUtil.generateToken(userEntity.getEmail(), userEntity.getRole(), userEntity.getFirstName(), userEntity.getLastName());

        return Map.of(
                "message", "Login successful",
                "token", token,
                "email", userEntity.getEmail(),
                "role", userEntity.getRole(),
                "firstName",userEntity.getFirstName(),
                "lastName", userEntity.getLastName()
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


}
