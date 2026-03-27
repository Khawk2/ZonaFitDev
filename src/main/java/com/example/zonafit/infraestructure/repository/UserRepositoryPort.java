package com.example.zonafit.infraestructure.repository;

import com.example.zonafit.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findById(Long idCliente);
    User save(User user);
}
