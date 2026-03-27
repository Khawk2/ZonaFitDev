package com.example.zonafit.service;

import com.example.zonafit.domain.model.Membership;
import com.example.zonafit.domain.model.Payment;
import com.example.zonafit.domain.model.User;
import com.example.zonafit.dto.MembershipPurchaseDTO;
import com.example.zonafit.dto.MembershipResponseDTO;
import com.example.zonafit.dto.PaymentResponseDTO;
import com.example.zonafit.infraestructure.Repository.MembershipRepositoryPort;
import com.example.zonafit.infraestructure.Repository.PaymentRepositoryPort;
import com.example.zonafit.infraestructure.Repository.UserRepository;
import com.example.zonafit.infraestructure.controller.utils.StatusMembership;
import com.example.zonafit.mapper.MembershipMapper;
import com.example.zonafit.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MembershipService {
    
    private final MembershipRepositoryPort membershipRepository;
    private final PaymentRepositoryPort paymentRepository;
    private final UserRepository userRepository;
    private final MembershipMapper membershipMapper;
    private final PaymentMapper paymentMapper;
    
    // Precios de las membresías
    private static final double MONTHLY_PRICE = 80000.0;
    private static final double QUARTERLY_PRICE = 216000.0; // 10% descuento
    private static final double YEARLY_PRICE = 768000.0; // 20% descuento
    private static final double VIP_PRICE = 100000.0;
    
    public MembershipResponseDTO purchaseMembership(MembershipPurchaseDTO purchaseDTO) {
        // Verificar que el usuario existe
        User user = userRepository.findById(purchaseDTO.userId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + purchaseDTO.userId()));
        
        // Verificar que el usuario no tenga ya una membresía activa
        if (membershipRepository.findByUserId(purchaseDTO.userId()).isPresent()) {
            throw new RuntimeException("El usuario ya tiene una membresía activa");
        }
        
        // Calcular precio según el tipo de membresía
        double price = calculatePrice(purchaseDTO.type());
        
        // Calcular fechas de inicio y fin
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = calculateEndDate(startDate, purchaseDTO.type());
        
        // Crear la membresía
        Membership membership = Membership.builder()
                .type(purchaseDTO.type())
                .startDate(startDate)
                .endDate(endDate)
                .status(StatusMembership.ACTIVE)
                .price(price)
                .user(user)
                .build();
        
        Membership savedMembership = membershipRepository.save(membership);
        
        // Crear el pago
        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(price));
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentMethod(purchaseDTO.paymentMethod());
        payment.setUser(user);
        payment.setMembership(savedMembership);
        
        paymentRepository.save(payment);
        
        return membershipMapper.toResponseDTO(savedMembership);
    }
    
    @Transactional(readOnly = true)
    public MembershipResponseDTO getMembershipByUserId(Long userId) {
        Membership membership = membershipRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No se encontró membresía para el usuario con ID: " + userId));
        return membershipMapper.toResponseDTO(membership);
    }
    
    @Transactional(readOnly = true)
    public List<PaymentResponseDTO> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId)
                .stream()
                .map(paymentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    public void cancelMembership(Long userId) {
        Membership membership = membershipRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No se encontró membresía para el usuario con ID: " + userId));
        
        membership.setStatus(StatusMembership.INACTIVE);
        membershipRepository.save(membership);
    }
    
    private double calculatePrice(com.example.zonafit.infraestructure.controller.utils.TypeMembership type) {
        return switch (type) {
            case MONTHLY -> MONTHLY_PRICE;
            case QUARTERLY -> QUARTERLY_PRICE;
            case YEARLY -> YEARLY_PRICE;
            case VIP -> VIP_PRICE;
        };
    }
    
    private LocalDate calculateEndDate(LocalDate startDate, com.example.zonafit.infraestructure.controller.utils.TypeMembership type) {
        return switch (type) {
            case MONTHLY -> startDate.plusMonths(1);
            case QUARTERLY -> startDate.plusMonths(3);
            case YEARLY -> startDate.plusYears(1);
            case VIP -> startDate.plusMonths(1); // VIP también es mensual pero con más beneficios
        };
    }
}
