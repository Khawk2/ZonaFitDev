package com.example.zonafit.mapper;

import com.example.zonafit.domain.model.Membership;
import com.example.zonafit.dto.membership.MembershipResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class MembershipMapper {
    
    public MembershipResponseDTO toResponseDTO(Membership membership) {
        return new MembershipResponseDTO(
                membership.getId(),
                membership.getType(),
                membership.getStartDate(),
                membership.getEndDate(),
                membership.getStatus(),
                membership.getPrice(),
                membership.getUser().getId()
        );
    }
}
