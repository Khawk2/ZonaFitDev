package com.example.zonafit.application.userservice.impl;

import com.example.zonafit.application.userservice.IUserService;
import com.example.zonafit.domain.model.User;
import com.example.zonafit.dto.MembershipPurchaseDTO;
import com.example.zonafit.dto.UserRequestDTO;
import com.example.zonafit.dto.UserResponseDTO;
import com.example.zonafit.dto.UserUpdateDTO;
import com.example.zonafit.infraestructure.repository.UserRepository;
import com.example.zonafit.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements IUserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MembershipService membershipService;
    
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        // Validar que no existan usuarios con el mismo username, email o documentNumber
        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new RuntimeException("Username already exists: " + userRequestDTO.getUsername());
        }
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new RuntimeException("Email already exists: " + userRequestDTO.getEmail());
        }
        if (userRepository.existsByDocumentNumber(userRequestDTO.getDocumentNumber())) {
            throw new RuntimeException("Document number already exists: " + userRequestDTO.getDocumentNumber());
        }
        
        User user = userMapper.toEntity(userRequestDTO);
        User savedUser = userRepository.save(user);
        
        // Crear membresía automáticamente para el nuevo usuario
        MembershipPurchaseDTO membershipPurchase = new MembershipPurchaseDTO(
                savedUser.getId(),
                userRequestDTO.getMembershipType(),
                userRequestDTO.getPaymentMethod()
        );
        
        membershipService.purchaseMembership(membershipPurchase);
        
        // Recargar el usuario con la membresía
        User userWithMembership = userRepository.findById(savedUser.getId())
                .orElseThrow(() -> new RuntimeException("Error al cargar el usuario con membresía"));
        
        return userMapper.toResponseDTO(userWithMembership);
    }
    
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.toResponseDTO(user);
    }
    
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getAllUsersPaginated(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponseDTO);
    }
    
    public UserResponseDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        // Validar unicidad si se están actualizando campos únicos
        if (userUpdateDTO.getUsername() != null && 
            !userUpdateDTO.getUsername().equals(user.getUsername()) &&
            userRepository.existsByUsername(userUpdateDTO.getUsername())) {
            throw new RuntimeException("Username already exists: " + userUpdateDTO.getUsername());
        }
        
        if (userUpdateDTO.getEmail() != null && 
            !userUpdateDTO.getEmail().equals(user.getEmail()) &&
            userRepository.existsByEmail(userUpdateDTO.getEmail())) {
            throw new RuntimeException("Email already exists: " + userUpdateDTO.getEmail());
        }
        
        if (userUpdateDTO.getDocumentNumber() != null && 
            !userUpdateDTO.getDocumentNumber().equals(user.getDocumentNumber()) &&
            userRepository.existsByDocumentNumber(userUpdateDTO.getDocumentNumber())) {
            throw new RuntimeException("Document number already exists: " + userUpdateDTO.getDocumentNumber());
        }
        
        userMapper.updateEntityFromDTO(userUpdateDTO, user);
        User updatedUser = userRepository.save(user);
        return userMapper.toResponseDTO(updatedUser);
    }
    
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public UserResponseDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        return userMapper.toResponseDTO(user);
    }
    
    @Transactional(readOnly = true)
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return userMapper.toResponseDTO(user);
    }
}
