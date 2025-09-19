package com.example.zonafit.infraestructure.dto;

import com.example.zonafit.domain.model.User;

public interface ActualizarClienteUseCase {
    User actualizarCliente(Long idCliente, User user);
}