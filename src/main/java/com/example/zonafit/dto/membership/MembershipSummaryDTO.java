package com.example.zonafit.dto.membership;

import java.time.LocalDate;

public record MembershipSummaryDTO(
        Long id,
        String type,
        String status,
        LocalDate endDate
) {
}
