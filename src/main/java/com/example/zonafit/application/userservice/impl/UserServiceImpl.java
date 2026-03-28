package com.example.zonafit.application.userservice.impl;

import com.example.zonafit.application.membershipservice.IMembershipService;
import com.example.zonafit.application.userservice.IUserService;
import com.example.zonafit.domain.exception.BusinessException;
import com.example.zonafit.domain.exception.ResourceNotFoundException;
import com.example.zonafit.domain.model.User;
import com.example.zonafit.domain.validator.UserValidator;
import com.example.zonafit.dto.membership.MembershipPurchaseDTO;
import com.example.zonafit.dto.user.UserRequestDTO;
import com.example.zonafit.dto.user.UserResponseDTO;
import com.example.zonafit.dto.user.UserUpdateDTO;
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
    private final IMembershipService membershipService;
    private final UserValidator userValidator;
    
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {

        // Validar que no existan usuarios con el mismo username, email o documentNumber
        userValidator.validateForCreate(userRequestDTO);

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
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
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
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        // Validar unicidad si se están actualizando campos únicos
        userValidator.validateForUpdate(userUpdateDTO, user);
        
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
