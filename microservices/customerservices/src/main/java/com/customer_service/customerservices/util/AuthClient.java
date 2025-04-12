package com.customer_service.customerservices.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthClient {

    private final RestTemplate restTemplate;

    public String getEmailFromToken(String token) {
        return getUserFieldFromAuth(token, "email");
    }

    public String getFirstName(String token) {
        return getUserFieldFromAuth(token, "firstName");
    }

    public String getLastName(String token) {
        return getUserFieldFromAuth(token, "lastName");
    }

    private String getUserFieldFromAuth(String token, String field) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        // Replace this with your Auth service base URL
        String AUTH_SERVICE_URL = "http://localhost:8050/request/auth";
        ResponseEntity<Map> response = restTemplate.exchange(
                AUTH_SERVICE_URL + "/me",
                HttpMethod.GET,
                request,
                Map.class
        );

        return (String) Objects.requireNonNull(response.getBody()).get(field);
    }
}
