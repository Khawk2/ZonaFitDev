package com.example.zonafit.dto.auth;

import lombok.Builder;

@Builder
public record AuthResponseDTO(
        String token,
        String type,
        long expiration
) {}