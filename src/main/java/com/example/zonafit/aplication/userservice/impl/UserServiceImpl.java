package com.example.zonafit.aplication.userservice.impl;

import com.example.zonafit.aplication.userservice.IUserService;
import com.example.zonafit.domain.model.User;
import com.example.zonafit.infraestructure.Repository.ActualizarUserUseCase;
import com.example.zonafit.infraestructure.Repository.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class UserServiceImpl implements IUserService, ActualizarUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public User actualizar(Long idCliente, User user) {
        User existente = userRepositoryPort.findById(idCliente)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con ID: " + idCliente));

        // Actualizamos solo los campos que deseas permitir
        existente.setNombre(user.getNombre());
        existente.setApellido(user.getApellido());
        existente.setTipoDocumento(user.getTipoDocumento());
        existente.setNumeroDocumento(user.getNumeroDocumento());
        existente.setFechaNacimiento(user.getFechaNacimiento());
        existente.setSexo(user.getSexo());
        existente.setTelefono(user.getTelefono());
        existente.setEmail(user.getEmail());
        existente.setDireccion(user.getDireccion());
        existente.setEstado(user.isEstado());

        return userRepositoryPort.save(existente);
    }
}
