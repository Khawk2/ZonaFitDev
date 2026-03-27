package com.example.zonafit.dto;

import com.example.zonafit.infraestructure.controller.utils.DocumentType;
import com.example.zonafit.infraestructure.controller.utils.Role;
import com.example.zonafit.infraestructure.controller.utils.StatusUser;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserResponseDTO(
    Long id,
    String username,
    String email,
    String firstName,
    String lastName,
    String phoneNumber,
    DocumentType documentType,
    String documentNumber,
    LocalDate birthDate,
    Role role,
    StatusUser status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    MembershipResponseDTO membership
) {
}
