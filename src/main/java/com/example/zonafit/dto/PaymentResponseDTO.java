package com.example.zonafit.dto;

import com.example.zonafit.infraestructure.controller.utils.PaymentMethod;

import java.time.LocalDate;

public record PaymentResponseDTO(
        Long id,
        Double amount,
        LocalDate paymentDate,
        PaymentMethod paymentMethod,
        Long userId,
        Long membershipId
) {
}
