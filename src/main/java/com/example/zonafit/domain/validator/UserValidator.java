package com.example.zonafit.domain.validator;

import com.example.zonafit.domain.exception.BusinessException;
import com.example.zonafit.domain.model.User;
import com.example.zonafit.dto.user.UserRequestDTO;
import com.example.zonafit.dto.user.UserUpdateDTO;
import com.example.zonafit.infraestructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateForCreate(UserRequestDTO dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new BusinessException("Username already exists: " + dto.getUsername());
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email already exists: " + dto.getEmail());
        }

        if (userRepository.existsByDocumentNumber(dto.getDocumentNumber())) {
            throw new BusinessException("Document number already exists: " + dto.getDocumentNumber());
        }
    }

    public void validateForUpdate(UserUpdateDTO dto, User currentUser) {

        if (dto.getUsername() != null &&
                !dto.getUsername().equals(currentUser.getUsername()) &&
                userRepository.existsByUsername(dto.getUsername())) {

            throw new BusinessException("Username already exists: " + dto.getUsername());
        }

        if (dto.getEmail() != null &&
                !dto.getEmail().equals(currentUser.getEmail()) &&
                userRepository.existsByEmail(dto.getEmail())) {

            throw new BusinessException("Email already exists: " + dto.getEmail());
        }

        if (dto.getDocumentNumber() != null &&
                !dto.getDocumentNumber().equals(currentUser.getDocumentNumber()) &&
                userRepository.existsByDocumentNumber(dto.getDocumentNumber())) {

            throw new BusinessException("Document number already exists: " + dto.getDocumentNumber());
        }
    }
}
