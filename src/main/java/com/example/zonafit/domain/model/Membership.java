package com.example.zonafit.domain.model;


import com.example.zonafit.infraestructure.controller.utils.StatusMembership;
import com.example.zonafit.infraestructure.controller.utils.TypeMembership;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="memberships")
public class Membership {

    @Id
    private Long id;
    private TypeMembership type;
    private LocalDate startDate;
    private LocalDate endDate;
    private StatusMembership status;
}
