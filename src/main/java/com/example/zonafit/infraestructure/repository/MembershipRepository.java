package com.example.zonafit.infraestructure.repository;

import com.example.zonafit.domain.model.Membership;
import com.example.zonafit.infraestructure.controller.utils.StatusMembership;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MembershipRepository implements MembershipRepositoryPort {
    
    private final MembershipJpaRepository membershipJpaRepository;
    
    @Override
    public Membership save(Membership membership) {
        return membershipJpaRepository.save(membership);
    }
    
    @Override
    public Optional<Membership> findById(Long id) {
        return membershipJpaRepository.findById(id);
    }
    
    @Override
    public Optional<Membership> findByUserId(Long userId) {
        return membershipJpaRepository.findByUserId(userId);
    }

    @Override
    public boolean existsByUserIdAndStatus(Long userId, StatusMembership status) {
        return false;
    }

    @Override
    public void deleteById(Long id) {
        membershipJpaRepository.deleteById(id);
    }
}
