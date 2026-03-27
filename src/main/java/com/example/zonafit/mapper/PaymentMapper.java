package com.example.zonafit.mapper;

import com.example.zonafit.domain.model.Payment;
import com.example.zonafit.dto.PaymentResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentResponseDTO toResponseDTO(Payment payment) {
        if (payment == null) {
            return null;
        }

        Long userId = null;
        if (payment.getUser() != null) {
            userId = payment.getUser().getId();
        }

        Long membershipId = null;
        if (payment.getMembership() != null) {
            membershipId = payment.getMembership().getId();
        }

        return new PaymentResponseDTO(
                payment.getId(),
                payment.getAmount(),
                payment.getPaymentDate(),
                payment.getPaymentMethod(),
                userId,
                membershipId
        );
    }
}