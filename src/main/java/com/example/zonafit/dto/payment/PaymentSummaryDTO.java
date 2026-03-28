package com.example.zonafit.dto.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentSummaryDTO(
        Long id,
        BigDecimal amount,
        LocalDate paymentDate
) {
}
