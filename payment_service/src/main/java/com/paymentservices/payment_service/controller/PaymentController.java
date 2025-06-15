package com.paymentservices.payment_service.controller;

import com.paymentservices.payment_service.dto.PaymentDTO;
import com.paymentservices.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentDTO> processPayment(@RequestParam String nonce, @RequestBody PaymentDTO dto) {
        return ResponseEntity.ok(paymentService.processPayment(nonce, dto));
    }

    // Generate client token for frontend
    @GetMapping("/client-token")
    public ResponseEntity<String> generateClientToken() {
        String clientToken = paymentService.generateClientToken();
        return ResponseEntity.ok(clientToken);
    }

}
