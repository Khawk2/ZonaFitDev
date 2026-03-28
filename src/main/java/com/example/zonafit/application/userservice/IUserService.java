package com.example.zonafit.application.userservice;

import com.example.zonafit.dto.user.UserRequestDTO;
import com.example.zonafit.dto.user.UserResponseDTO;
import com.example.zonafit.dto.user.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    UserResponseDTO getUserById(Long id);

    List<UserResponseDTO> getAllUsers();

    Page<UserResponseDTO> getAllUsersPaginated(Pageable pageable);

    UserResponseDTO updateUser(Long id, UserUpdateDTO userUpdateDTO);

    void deleteUser(Long id);

    UserResponseDTO getUserByUsername(String username);

    UserResponseDTO getUserByEmail(String email);
}
