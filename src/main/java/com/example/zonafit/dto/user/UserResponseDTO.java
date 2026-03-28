package com.example.zonafit.dto.user;

import com.example.zonafit.domain.enums.DocumentType;
import com.example.zonafit.domain.enums.Role;
import com.example.zonafit.domain.enums.StatusUser;
import com.example.zonafit.dto.membership.MembershipResponseDTO;

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
