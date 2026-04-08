package com.example.zonafit.infraestructure.controller;

import com.example.zonafit.dto.auth.AuthResponseDTO;
import com.example.zonafit.dto.user.UserLoginDTO;
import com.example.zonafit.dto.user.UserRequestDTO;
import com.example.zonafit.dto.user.UserResponseDTO;
import com.example.zonafit.application.userservice.IUserService;
import com.example.zonafit.security.JwtService;
import com.example.zonafit.domain.enums.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody UserLoginDTO loginDTO) {

        Authentication authentication = authenticate(loginDTO);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(
                AuthResponseDTO.builder()
                        .token(token)
                        .type("Bearer")
                        .build()
        );
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody UserRequestDTO dto) {

        UserResponseDTO user = userService.createUser(dto);

        //Auto-login después de registrarse
        UserDetails userDetails = buildUserDetailsFromUser(user);

        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AuthResponseDTO.builder()
                        .token(token)
                        .type("Bearer")
                        .build());
    }

    private Authentication authenticate(UserLoginDTO loginDTO) {
        try {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.email(),
                            loginDTO.password()
                    )
            );
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid email or password");
        } catch (DisabledException ex) {
            throw new BadCredentialsException("Account is disabled");
        } catch (LockedException ex) {
            throw new BadCredentialsException("Account is locked");
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Authentication failed");
        }
    }

    private UserDetails buildUserDetailsFromUser(UserResponseDTO user) {
        Role userRole = user.role() != null ? user.role() : Role.USER;
        
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.email())
                .password("") //no necesario aquí
                .authorities(getRoleAuthority(userRole))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    private String getRoleAuthority(Role role) {
        return "ROLE_" + role.name();
    }
}