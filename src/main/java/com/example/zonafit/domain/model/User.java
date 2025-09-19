package com.example.zonafit.domain.model;

import com.example.zonafit.infraestructure.controller.utils.DocumentType;
import com.example.zonafit.infraestructure.controller.utils.Role;
import com.example.zonafit.infraestructure.controller.utils.StatusUser;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="users")
public class User {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private DocumentType documentType;
    private String documentNumber;
    private LocalDate birthDate;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private StatusUser status;
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Payment> payments;



}
