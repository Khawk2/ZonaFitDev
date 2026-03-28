package com.example.zonafit.application.membershipservice;

import com.example.zonafit.dto.membership.MembershipPurchaseDTO;
import com.example.zonafit.dto.membership.MembershipResponseDTO;
import com.example.zonafit.dto.payment.PaymentResponseDTO;

import java.util.List;

public interface IMembershipService {

    MembershipResponseDTO purchaseMembership(MembershipPurchaseDTO purchaseDTO);

    MembershipResponseDTO getMembershipByUserId(Long userId);

    List<PaymentResponseDTO> getPaymentsByUserId(Long userId);

    void cancelMembership(Long userId);

}
