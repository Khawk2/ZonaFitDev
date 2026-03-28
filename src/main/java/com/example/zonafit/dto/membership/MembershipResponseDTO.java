package com.example.zonafit.dto.membership;

import com.example.zonafit.domain.enums.StatusMembership;
import com.example.zonafit.domain.enums.TypeMembership;

import java.time.LocalDate;

public record MembershipResponseDTO(
        Long id,
        TypeMembership type,
        LocalDate startDate,
        LocalDate endDate,
        StatusMembership status,
        Double price,
        Long userId
) {


}
