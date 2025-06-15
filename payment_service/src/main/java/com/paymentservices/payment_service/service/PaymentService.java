package com.paymentservices.payment_service.service;

import com.braintreegateway.*;
import com.paymentservices.payment_service.dto.PaymentDTO;
import com.paymentservices.payment_service.entity.PaymentEntity;
import com.paymentservices.payment_service.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private BraintreeGateway braintreeGateway;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PaymentDTO processPayment(String nonce, PaymentDTO dto) {
        TransactionRequest request = new TransactionRequest()
                .amount(BigDecimal.valueOf(dto.getAmount()))
                .paymentMethodNonce(nonce)
                .options().submitForSettlement(true).done();

        Result<Transaction> result = braintreeGateway.transaction().sale(request);

        if (result.isSuccess()) {
            Transaction transaction = result.getTarget();
            dto.setPaymentId(transaction.getId());
            dto.setPaymentStatus(transaction.getStatus().toString());
            dto.setPaymentDate(LocalDateTime.now());

            PaymentEntity entity = modelMapper.map(dto, PaymentEntity.class);
            paymentRepository.save(entity);
            return dto;
        } else {
            throw new RuntimeException("Payment Failed: " + result.getMessage());
        }
    }


    public String generateClientToken() {
        return braintreeGateway.clientToken().generate();
    }

}
