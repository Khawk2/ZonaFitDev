package com.example.zonafit.dto.membership;

import com.example.zonafit.domain.enums.PaymentMethod;
import com.example.zonafit.domain.enums.TypeMembership;
import jakarta.validation.constraints.NotNull;

public record MembershipPurchaseDTO(

        @NotNull(message = "User ID is required")
        Long userId,
        
        @NotNull(message = "Membership type is required")
        TypeMembership type,
        
        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod
) {
}
