package com.example.zonafit.mapper;

import com.example.zonafit.domain.model.User;
import com.example.zonafit.dto.UserRequestDTO;
import com.example.zonafit.dto.UserResponseDTO;
import com.example.zonafit.dto.UserUpdateDTO;
import com.example.zonafit.infraestructure.controller.utils.StatusUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    
    public User toEntity(UserRequestDTO dto) {
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phoneNumber(dto.getPhoneNumber())
                .documentType(dto.getDocumentType())
                .documentNumber(dto.getDocumentNumber())
                .birthDate(dto.getBirthDate())
                .role(dto.getRole())
                .status(StatusUser.ACTIVE)
                .build();
    }
    
    public UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
    
    public void updateEntityFromDTO(UserUpdateDTO dto, User user) {
        if (dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }
        if (dto.getFirstName() != null) {
            user.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            user.setLastName(dto.getLastName());
        }
        if (dto.getPhoneNumber() != null) {
            user.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getDocumentType() != null) {
            user.setDocumentType(dto.getDocumentType());
        }
        if (dto.getDocumentNumber() != null) {
            user.setDocumentNumber(dto.getDocumentNumber());
        }
        if (dto.getBirthDate() != null) {
            user.setBirthDate(dto.getBirthDate());
        }
        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }
        if (dto.getStatus() != null) {
            user.setStatus(dto.getStatus());
        }
    }
}
