package com.project.java_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.java_backend.exception.ResourceNotFoundException;
import com.project.java_backend.model.Payment;
import com.project.java_backend.model.Ticket;
import com.project.java_backend.repository.PaymentRepository;
import com.project.java_backend.repository.TicketRepository;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TicketRepository ticketRepository;

    // Create a new payment
    public Payment createPayment(Double amount, String paymentMethod, Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + ticketId));

        Payment payment = new Payment(amount, paymentMethod, ticket);
        return paymentRepository.save(payment);
    }

    // Get a payment by ID
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id " + id));
    }

    // Get all payments associated with a specific ticket
    public List<Payment> getPaymentsByTicketId(Long ticketId) {
        return paymentRepository.findByTicketId(ticketId);
    }


    // Update payment details (e.g., if amount or payment method needs correction)
    public Payment updatePayment(Long id, Payment updatedPayment) {
        Payment existingPayment = getPaymentById(id);
        existingPayment.setAmount(updatedPayment.getAmount());
        existingPayment.setPaymentMethod(updatedPayment.getPaymentMethod());
        return paymentRepository.save(existingPayment);
    }

    // Delete a payment by ID
    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Payment not found with id " + id);
        }
        paymentRepository.deleteById(id);
    }
}

