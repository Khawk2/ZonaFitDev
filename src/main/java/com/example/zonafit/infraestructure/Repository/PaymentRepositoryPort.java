package com.example.zonafit.infraestructure.Repository;

import com.example.zonafit.domain.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepositoryPort {
    Payment save(Payment payment);
    Optional<Payment> findById(Long id);
    List<Payment> findByUserId(Long userId);
    List<Payment> findByMembershipId(Long membershipId);
}
