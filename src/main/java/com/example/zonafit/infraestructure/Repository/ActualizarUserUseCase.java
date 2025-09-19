package com.example.zonafit.infraestructure.Repository;

import com.example.zonafit.domain.model.User;

public interface ActualizarUserUseCase {
    User actualizar(Long idCliente, User user);
}
