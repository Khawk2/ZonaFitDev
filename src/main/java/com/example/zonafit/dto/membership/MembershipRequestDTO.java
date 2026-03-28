package com.example.zonafit.dto.membership;

import com.example.zonafit.domain.enums.TypeMembership;
import jakarta.validation.constraints.NotNull;

public record MembershipRequestDTO(
        @NotNull(message = "User ID is required")
        Long userId,
        
        @NotNull(message = "Membership type is required")
        TypeMembership type
) {
}
