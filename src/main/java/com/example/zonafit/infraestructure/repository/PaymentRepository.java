package com.example.zonafit.infraestructure.repository;

import com.example.zonafit.domain.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentRepository implements PaymentRepositoryPort {
    
    private final PaymentJpaRepository paymentJpaRepository;
    
    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(payment);
    }
    
    @Override
    public Optional<Payment> findById(Long id) {
        return paymentJpaRepository.findById(id);
    }
    
    @Override
    public List<Payment> findByUserId(Long userId) {
        return paymentJpaRepository.findByUserId(userId);
    }
    
    @Override
    public List<Payment> findByMembershipId(Long membershipId) {
        return paymentJpaRepository.findByMembershipId(membershipId);
    }
}
