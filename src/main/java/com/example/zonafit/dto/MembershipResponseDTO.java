package com.example.zonafit.dto;

import com.example.zonafit.infraestructure.controller.utils.StatusMembership;
import com.example.zonafit.infraestructure.controller.utils.TypeMembership;

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
