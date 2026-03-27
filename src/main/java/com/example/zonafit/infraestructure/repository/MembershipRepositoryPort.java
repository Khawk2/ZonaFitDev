package com.example.zonafit.infraestructure.repository;

import com.example.zonafit.domain.model.Membership;
import com.example.zonafit.infraestructure.controller.utils.StatusMembership;

import java.util.Optional;

public interface MembershipRepositoryPort {
    Membership save(Membership membership);
    Optional<Membership> findById(Long id);
    Optional<Membership> findByUserId(Long userId);

    boolean existsByUserIdAndStatus(Long userId, StatusMembership status); // 👈 agregar

    void deleteById(Long id);
}
