package com.example.zonafit.infraestructure.Repository;

import com.example.zonafit.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

}