package com.example.zonafit.dto.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentDetailDTO(
        Long id,
        BigDecimal amount,
        LocalDate paymentDate,
        String paymentMethod,
        Long membershipId
) {
}
