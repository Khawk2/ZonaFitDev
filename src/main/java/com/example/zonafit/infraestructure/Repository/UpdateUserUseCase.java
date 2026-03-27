package com.example.zonafit.infraestructure.Repository;

import com.example.zonafit.domain.model.User;

public interface UpdateUserUseCase {
    User update(Long idUser, User user);
}
