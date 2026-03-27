package com.example.zonafit.infraestructure.repository;

import com.example.zonafit.domain.model.User;

public interface UpdateUserUseCase {
    User update(Long idUser, User user);
}
