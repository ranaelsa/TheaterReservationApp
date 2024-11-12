package com.project.java_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.java_backend.model.Payment;
import com.project.java_backend.repository.PaymentRepository;
import com.project.java_backend.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Create a new payment with a dummy call to a third-party payment service
    public Payment createPayment(Double amount, String paymentMethod, String cardLastFourDigits) {
        // Simulate a call to a third-party payment provider
        String transactionId = processPaymentWithProvider(amount, paymentMethod);

        // Create and save the payment record with the transaction ID and masked card details
        Payment payment = new Payment(amount, paymentMethod, transactionId, cardLastFourDigits);
        return paymentRepository.save(payment);
    }

    // Simulate payment processing with a third-party provider
    private String processPaymentWithProvider(Double amount, String paymentMethod) {
        // This is a dummy implementation for the sake of example
        System.out.println("Processing payment of $" + amount + " via " + paymentMethod);

        // Simulate a successful payment by generating a unique transaction ID
        String transactionId = UUID.randomUUID().toString();
        System.out.println("Payment successful. Transaction ID: " + transactionId);

        // Return the transaction ID to save in the database
        return transactionId;
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
