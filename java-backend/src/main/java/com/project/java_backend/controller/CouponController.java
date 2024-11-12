package com.project.java_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.java_backend.model.Coupon;
import com.project.java_backend.service.CouponService;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

	@Autowired
	private CouponService couponService;

	// Create a coupon
	@PostMapping(consumes="application/json", produces="application/json")
	public ResponseEntity<Coupon> createCoupon(@RequestBody Double amount) {
		Coupon createdCoupon = couponService.createCoupon(amount);
		return ResponseEntity.ok(createdCoupon);
	}

	// Redeem a coupon
	@PutMapping(value="/redeem/{code}", consumes="application/json", produces="application/json")
	public ResponseEntity<Coupon> redeemCoupon(@PathVariable String code) {
		Coupon redeemedCoupon = couponService.redeemCoupon(code);
		return ResponseEntity.ok(redeemedCoupon);
	}
}
