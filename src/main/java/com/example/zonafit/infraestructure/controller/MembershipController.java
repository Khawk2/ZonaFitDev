package com.example.zonafit.infraestructure.controller;

import com.example.zonafit.application.membershipservice.IMembershipService;
import com.example.zonafit.dto.membership.MembershipPurchaseDTO;
import com.example.zonafit.dto.membership.MembershipResponseDTO;
import com.example.zonafit.dto.payment.PaymentResponseDTO;
import com.example.zonafit.application.membershipservice.impl.MembershipServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MembershipController {

    private final IMembershipService membershipService;

    @PostMapping
    public ResponseEntity<MembershipResponseDTO> purchaseMembership(
            @Valid @RequestBody MembershipPurchaseDTO purchaseDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(membershipService.purchaseMembership(purchaseDTO));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<MembershipResponseDTO> getMembershipByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(membershipService.getMembershipByUserId(userId));
    }

    @GetMapping("/user/{userId}/payments")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(membershipService.getPaymentsByUserId(userId));
    }

    @PutMapping("/user/{userId}/cancel")
    public ResponseEntity<Void> cancelMembership(@PathVariable Long userId) {
        membershipService.cancelMembership(userId);
        return ResponseEntity.ok().build();
    }
}