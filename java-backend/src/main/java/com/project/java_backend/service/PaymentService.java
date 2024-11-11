package com.project.java_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.java_backend.model.Payment;
import com.project.java_backend.repository.PaymentRepository;
import com.project.java_backend.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Create a new payment
    public Payment createPayment(Double amount, String paymentMethod) {
        Payment payment = new Payment(amount, paymentMethod);
        return paymentRepository.save(payment);
    }

    // Retrieve all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Retrieve a payment by ID
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id " + id));
    }

    // Update payment details (if needed)
    public Payment updatePayment(Long id, Double amount, String paymentMethod) {
        Payment existingPayment = getPaymentById(id);
        existingPayment.setAmount(amount);
        existingPayment.setPaymentMethod(paymentMethod);
        existingPayment.setPaymentDate(LocalDateTime.now());
        return paymentRepository.save(existingPayment);
    }

    // Delete a payment
    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Payment not found with id " + id);
        }
        paymentRepository.deleteById(id);
    }
}

