package com.project.java_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.java_backend.model.Coupon;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    // Find a coupon by its unique code
    Optional<Coupon> findByCouponCode(String couponCode);

    // Find all unredeemed coupons
    List<Coupon> findByStatus(String status);

    // Find all expired coupons
    List<Coupon> findByExpirationDateBefore(LocalDateTime currentDateTime);
}

