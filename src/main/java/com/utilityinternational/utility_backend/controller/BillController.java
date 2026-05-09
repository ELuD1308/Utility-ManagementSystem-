package com.utilityinternational.utility_backend.controller;

import com.utilityinternational.utility_backend.entity.Bill;
import com.utilityinternational.utility_backend.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillingService billingService;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Bill>> getBillsForCustomer(
            @PathVariable Long customerId) {
        return ResponseEntity.ok(
                billingService.getBillsForCustomer(customerId));
    }

    @GetMapping("/{billId}")
    public ResponseEntity<Bill> getBillById(
            @PathVariable Long billId) {
        return ResponseEntity.ok(
                billingService.getBillById(billId));
    }

    @GetMapping("/{billId}/calculate")
    public ResponseEntity<BigDecimal> calculateBill(
            @PathVariable Long billId) {
        return ResponseEntity.ok(
                billingService.calculateBill(billId));
    }

    @PutMapping("/{billId}/mark-paid")
    public ResponseEntity<Bill> markBillAsPaid(
            @PathVariable Long billId) {
        return ResponseEntity.ok(
                billingService.markBillAsPaid(billId));
    }
}