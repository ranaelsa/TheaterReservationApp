package com.project.java_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.java_backend.exception.ResourceNotFoundException;
import com.project.java_backend.model.Coupon;
import com.project.java_backend.repository.CouponRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    // Create a new coupon
    public Coupon createCoupon(Double amount) {
        Coupon coupon = new Coupon(amount); // Uses constructor to set expiration date, code, and default status
        return couponRepository.save(coupon);
    }

    // Get a coupon by its unique code
    public Coupon getCouponByCode(String couponCode) {
        return couponRepository.findByCouponCode(couponCode)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found with code " + couponCode));
    }

    // Update coupon status to redeemed
    public Coupon redeemCoupon(String couponCode) {
        Coupon coupon = getCouponByCode(couponCode);
        if (!coupon.getStatus().equals("Unredeemed") || isCouponExpired(couponCode)) {
            throw new IllegalStateException("Coupon has already been redeemed or is invalid.");
        }
        coupon.setStatus("Redeemed");
        return couponRepository.save(coupon);
    }

    // Check if a coupon is expired
    public boolean isCouponExpired(String couponCode) {
        Coupon coupon = getCouponByCode(couponCode);
        return coupon.getExpirationDate().isBefore(LocalDateTime.now());
    }

    // Delete expired coupons
    public void deleteExpiredCoupons() {
        List<Coupon> expiredCoupons = couponRepository.findByExpirationDateBefore(LocalDateTime.now());
        couponRepository.deleteAll(expiredCoupons);
    }

    // Delete a specific coupon by ID
    public void deleteCoupon(Long id) {
        if (!couponRepository.existsById(id)) {
            throw new ResourceNotFoundException("Coupon not found with id " + id);
        }
        couponRepository.deleteById(id);
    }
}

