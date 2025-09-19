package com.example.zonafit.domain.model;


import com.example.zonafit.infraestructure.controller.utils.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="payments")
public class Payment {

    @Id
    private Long id;

    private float amount;
    private LocalDate paymentDate;
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "idUser")
    private User user;


}
