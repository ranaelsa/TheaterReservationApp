package com.project.java_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.java_backend.model.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Find all payments associated with a specific ticket
    List<Payment> findByTicketId(Long ticketId);

}

