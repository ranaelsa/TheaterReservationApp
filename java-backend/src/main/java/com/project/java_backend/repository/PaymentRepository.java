package com.project.java_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.java_backend.model.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}

