package com.example.zonafit.infraestructure.repository;

import com.example.zonafit.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByDocumentNumber(String documentNumber);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    boolean existsByDocumentNumber(String documentNumber);
}
