package com.example.zonafit.infraestructure.controller;

import com.example.zonafit.dto.MembershipPurchaseDTO;
import com.example.zonafit.dto.MembershipResponseDTO;
import com.example.zonafit.dto.PaymentResponseDTO;
import com.example.zonafit.service.MembershipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membership")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MembershipController {

    private final MembershipService membershipService;
    
    @PostMapping("/purchase")
    public ResponseEntity<MembershipResponseDTO> purchaseMembership(@Valid @RequestBody MembershipPurchaseDTO purchaseDTO) {
        try {
            MembershipResponseDTO membership = membershipService.purchaseMembership(purchaseDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(membership);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<MembershipResponseDTO> getMembershipByUserId(@PathVariable Long userId) {
        try {
            MembershipResponseDTO membership = membershipService.getMembershipByUserId(userId);
            return ResponseEntity.ok(membership);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/user/{userId}/payments")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByUserId(@PathVariable Long userId) {
        List<PaymentResponseDTO> payments = membershipService.getPaymentsByUserId(userId);
        return ResponseEntity.ok(payments);
    }
    
    @PutMapping("/user/{userId}/cancel")
    public ResponseEntity<Void> cancelMembership(@PathVariable Long userId) {
        try {
            membershipService.cancelMembership(userId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
