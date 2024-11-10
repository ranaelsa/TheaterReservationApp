package com.project.java_backend.model;

import jakarta.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
    private Double amount;

    @NotNull(message = "Expiration date is required")
    private LocalDateTime expirationDate;

    @NotBlank(message = "Coupon code is required")
    @Column(unique = true)
    private String couponCode;

    @NotBlank(message = "Status is required")
    private String status;

    // Default constructor for JPA
    public Coupon() {
    }

    // Constructor with amount only; other fields auto-generated
    public Coupon(Double amount) {
        this.amount = amount;
        this.expirationDate = generateExpirationDate();
        this.couponCode = generateCouponCode();
        this.status = "Unredeemed"; // Default status for new coupons
    }

    // Helper methods to generate expiration date and coupon code
    private LocalDateTime generateExpirationDate() {
        return LocalDateTime.now().plusYears(1); // Set to one year from creation date
    }

    private String generateCouponCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase(); // Unique 8-character code
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
