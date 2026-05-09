package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.entity.Bill;
import com.utilityinternational.utility_backend.enums.BillStatus;
import com.utilityinternational.utility_backend.exception.ResourceNotFoundException;
import com.utilityinternational.utility_backend.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final BillRepository billRepository;

    @Override
    public List<Bill> getBillsForCustomer(Long customerId) {
        return billRepository.findByCustomerId(customerId);
    }

    @Override
    public Bill getBillById(Long billId) {
        return billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + billId));
    }

    @Override
    public BigDecimal calculateBill(Long billId) {
        Bill bill = getBillById(billId);
        return bill.getAmount(); // external billing system already calculates this
    }

    @Override
    public Bill markBillAsPaid(Long billId) {
        Bill bill = getBillById(billId);
        bill.setStatus(BillStatus.PAID);
        return billRepository.save(bill);
    }
}