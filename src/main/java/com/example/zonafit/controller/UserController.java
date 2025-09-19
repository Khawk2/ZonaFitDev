package com.example.zonafit.controller;

import com.example.zonafit.dto.UserResponseDTO;
import com.example.zonafit.mapper.UserMapper;
import com.example.zonafit.service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UsuarioController {

    private final UserService userService;

    public UsuarioController(UserService userService) {
        this.userService = userService;
    }

    // CREATE
    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserResponseDTO dto) {
        com.zonafit.app.usuario.model.User user = UserMapper.toEntity(dto);
        return UserMapper.toResponseDTO(userServicce.crearUsuario(user));
    }

    // READ ALL
    @GetMapping
    public List<UserResponseDTO> listUsers() {
        return userService.listUsers()
                .stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // READ ONE
    @GetMapping("/{id}")
    public UsuarioResponseDTO obtenerUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return UsuarioMapper.toResponseDTO(usuario);
    }

    // UPDATE
    @PutMapping("/{id}")
    public UsuarioResponseDTO actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDTO dto) {
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, dto);
        return UsuarioMapper.toResponseDTO(usuarioActualizado);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }
}
