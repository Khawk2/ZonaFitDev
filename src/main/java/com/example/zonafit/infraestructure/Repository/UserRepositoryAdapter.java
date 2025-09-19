package com.example.zonafit.infraestructure.Repository;

import com.example.zonafit.domain.model.User;
import com.example.zonafit.infraestructure.Jpa.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserRepositoryAdapter {
    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findById(Long idCliente) {
        return userJpaRepository.findById(idCliente).map(this::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity entity = toEntity(user);
        return toDomain(userJpaRepository.save(entity));
    }

    private User toDomain(UserEntity entity) {
        User user = new User();
        user.setIdCliente(entity.getIdCliente());
        user.setNombre(entity.getNombre());
        user.setApellido(entity.getApellido());
        user.setTipoDocumento(entity.getTipoDocumento());
        user.setNumeroDocumento(entity.getNumeroDocumento());
        user.setFechaNacimiento(entity.getFechaNacimiento());
        user.setSexo(entity.getSexo());
        user.setTelefono(entity.getTelefono());
        user.setEmail(entity.getEmail());
        user.setDireccion(entity.getDireccion());
        user.setFechaRegistro(entity.getFechaRegistro());
        user.setEstado(entity.isEstado());
        return user;
    }

    private UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setIdCliente(user.getIdCliente());
        entity.setNombre(user.getNombre());
        entity.setApellido(user.getApellido());
        entity.setTipoDocumento(user.getTipoDocumento());
        entity.setNumeroDocumento(user.getNumeroDocumento());
        entity.setFechaNacimiento(user.getFechaNacimiento());
        entity.setSexo(user.getSexo());
        entity.setTelefono(user.getTelefono());
        entity.setEmail(user.getEmail());
        entity.setDireccion(user.getDireccion());
        entity.setFechaRegistro(user.getFechaRegistro());
        entity.setEstado(user.isEstado());
        return entity;
    }
}
