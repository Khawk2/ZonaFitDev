package com.example.zonafit.dto.payment;

import com.example.zonafit.domain.enums.PaymentMethod;

import java.time.LocalDate;

public record PaymentResponseDTO(
        Long id,
        java.math.BigDecimal amount,
        LocalDate paymentDate,
        PaymentMethod paymentMethod,
        Long userId,
        Long membershipId
) {
}
