package com.example.zonafit.infraestructure.controller;

import com.example.zonafit.domain.model.User;
import com.example.zonafit.infraestructure.dto.ActualizarClienteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class UserController {
    private  ActualizarClienteUseCase actualizarClienteUseCase;

    @PutMapping("/{id}")
    public ResponseEntity<User> actualizarCliente(
            @PathVariable Long id,
            @RequestBody User user
    ) {
        return ResponseEntity.ok(actualizarClienteUseCase.actualizarCliente(id, user));
    }
}