package com.example.zonafit.dto.payment;

public record PaymentRequestDTO(
        Long userId,
        Long membershipId,
        String paymentMethod
) {
}
