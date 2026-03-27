package com.example.zonafit.infraestructure.repository;

import com.example.zonafit.domain.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipJpaRepository extends JpaRepository<Membership, Long> {
    Optional<Membership> findByUserId(Long userId);
}
