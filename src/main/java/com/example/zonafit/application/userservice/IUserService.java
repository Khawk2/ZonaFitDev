package com.example.zonafit.application.userservice;

import com.example.zonafit.dto.user.UserRequestDTO;
import com.example.zonafit.dto.user.UserResponseDTO;
import com.example.zonafit.dto.user.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.List;

public interface IUserService {

    UserResponseDTO createUser(@NonNull UserRequestDTO userRequestDTO);

    UserResponseDTO getUserById(@NonNull Long id);

    List<UserResponseDTO> getAllUsers();

    Page<UserResponseDTO> getAllUsersPaginated(Pageable pageable);

    UserResponseDTO updateUser(@NonNull Long id, @NonNull UserUpdateDTO userUpdateDTO);

    void deleteUser(@NonNull Long id);

    UserResponseDTO getUserByUsername(@NonNull String username);

    UserResponseDTO getUserByEmail(@NonNull String email);
}
