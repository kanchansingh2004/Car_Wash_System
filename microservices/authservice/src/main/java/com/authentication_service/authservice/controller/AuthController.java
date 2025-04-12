package com.authentication_service.authservice.controller;

import com.authentication_service.authservice.dto.*;
import com.authentication_service.authservice.entity.AuthUserEntity;
import com.authentication_service.authservice.service.AuthService;
import com.authentication_service.authservice.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/request/auth")
public class AuthController {
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authService;

    //Generate token
    @GetMapping("/token")
    public Map<String, String> generateToken(@RequestParam String email, @RequestParam String role, @RequestParam String firstName, @RequestParam String lastName){
        String token = jwtUtil.generateToken(email, role, firstName, lastName);
        return Map.of("token", token);
    }

    //Validate token and extract claims
    @GetMapping("/validate")
    public Map<String, Object> validateToken(@RequestParam String email, @RequestParam String token){
        boolean isValid = jwtUtil.validateToken(token,email);
        String role = jwtUtil.extractRole(token);
        String issuedAt = jwtUtil.extractIssuedAt(token).toString();
        String expiration = jwtUtil.extractExpiration(token).toString();

        return Map.of(
                "isValid", isValid,
                "email", email,
                "role", role,
                "issuedAt",issuedAt,
                "expiration", expiration
        );
    }

    //Register new user
    @PostMapping("/signup")
    public Map<String, String> registerUser(@RequestBody SignupRequest userDTO){
        return Map.of("Message", authService.registerUser(userDTO));
    }

    //Login Existing user and return jwt token
    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody AuthUserDTO userDTO){
        return authService.login(userDTO);
    }

    //Get current user details
    @PostMapping("/me")
    public ResponseEntity<AuthProfileDTO> getProfile(HttpServletRequest request) {
        // Extract token from the Authorization header
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(403).build(); // Forbidden if no proper header
        }

        String token = authHeader.substring(7); // Remove "Bearer " prefix

        // Extract details from token
        AuthProfileDTO user = new AuthProfileDTO();
        user.setEmail(jwtUtil.extractEmail(token));
        user.setRole(jwtUtil.extractRole(token));
        user.setFirstName(jwtUtil.extractFirstName(token));
        user.setLastName(jwtUtil.extractLastName(token));

        return ResponseEntity.ok(user);
    }

    //Reset password
    @PutMapping("/resetPassword")
    public Map<String,String> resetPassword(@RequestBody AuthUserDTO dto){
        return authService.resetPassword(dto);
    }

    //Refresh token
    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshAccessToken(@RequestBody RefreshTokenRequest request) {
        try {
            String refreshToken = request.getRefreshToken();
            if (jwtUtil.validateToken(refreshToken, jwtUtil.extractEmail(refreshToken))) {
                String email = jwtUtil.extractEmail(refreshToken);

                String newAccessToken = jwtUtil.generateToken(email);
                return ResponseEntity.ok(new TokenResponse(newAccessToken));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenResponse("Invalid refresh token"));
        }
    }

}
