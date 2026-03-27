package com.example.zonafit.infraestructure.Repository;

import com.example.zonafit.domain.model.Membership;

import java.util.Optional;

public interface MembershipRepositoryPort {
    Membership save(Membership membership);
    Optional<Membership> findById(Long id);
    Optional<Membership> findByUserId(Long userId);
    void deleteById(Long id);
}
