package com.example.zonafit.dto;

import com.example.zonafit.infraestructure.controller.utils.DocumentType;
import com.example.zonafit.infraestructure.controller.utils.Role;
import com.example.zonafit.infraestructure.controller.utils.StatusUser;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;
    
    @Email(message = "Email must be valid")
    private String email;
    
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    @Size(max = 100, message = "First name must not exceed 100 characters")
    private String firstName;
    
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    private String lastName;
    
    @Pattern(regexp = "^[0-9+\\-\\s()]+$", message = "Phone number must contain only numbers, +, -, spaces, and parentheses")
    private String phoneNumber;
    
    private DocumentType documentType;
    
    @Size(max = 20, message = "Document number must not exceed 20 characters")
    private String documentNumber;
    
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
    
    private Role role;
    
    private StatusUser status;
}
