package com.example.zonafit.application.userservice.impl;

import com.example.zonafit.application.membershipservice.IMembershipService;
import com.example.zonafit.application.userservice.IUserService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IMembershipService membershipService;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO createUser(@NonNull UserRequestDTO dto) {
        Assert.notNull(dto, "UserRequestDTO no puede ser nulo");

        userValidator.validateForCreate(dto);

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        createMembershipForUser(savedUser, dto);

        return userMapper.toResponseDTO(savedUser);
    }

    private void createMembershipForUser(@NonNull User user, @NonNull UserRequestDTO dto) {
        Assert.notNull(user.getId(), "User ID no puede ser nulo después de guardar");

        try {
            MembershipPurchaseDTO membership = new MembershipPurchaseDTO(
                    user.getId(),
                    dto.getMembershipType(),
                    dto.getPaymentMethod()
            );

            membershipService.purchaseMembership(membership);
            log.info("Membresía creada exitosamente para usuario: {}", user.getId());
        } catch (Exception ex) {
            log.warn("No se pudo crear membresía para usuario {}: {}", user.getId(), ex.getMessage());
            // No fallar el registro del usuario si la membresía falla
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(@NonNull Long id) {
        Assert.notNull(id, "ID no puede ser nulo");
        return userRepository.findById(id)
                .map(userMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Page<UserResponseDTO> getAllUsersPaginated(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponseDTO);
    }

    @Override
    public UserResponseDTO updateUser(@NonNull Long id, @NonNull UserUpdateDTO dto) {
        Assert.notNull(id, "ID no puede ser nulo");
        Assert.notNull(dto, "UserUpdateDTO no puede ser nulo");

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        userValidator.validateForUpdate(dto, user);

        userMapper.updateEntityFromDTO(dto, user);

        User savedUser = userRepository.save(user);
        Assert.notNull(savedUser, "Usuario guardado no puede ser nulo");
        return userMapper.toResponseDTO(savedUser);
    }

    @Override
    public void deleteUser(@NonNull Long id) {
        Assert.notNull(id, "ID no puede ser nulo");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        userRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserByUsername(@NonNull String username) {
        Assert.hasText(username, "Username no puede ser nulo o vacío");
        return userRepository.findByUsername(username)
                .map(userMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con username: " + username));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserByEmail(@NonNull String email) {
        Assert.hasText(email, "Email no puede ser nulo o vacío");
        return userRepository.findByEmail(email)
                .map(userMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
    }
}