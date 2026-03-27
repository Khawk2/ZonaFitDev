package com.example.zonafit.mapper;

import com.example.zonafit.domain.model.Payment;
import com.example.zonafit.dto.PaymentResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    
    public PaymentResponseDTO toResponseDTO(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                (double) payment.getAmount(),
                payment.getPaymentDate(),
                payment.getPaymentMethod(),
                payment.getUser().getId(),
                payment.getMembership().getId()
        );
    }
}
