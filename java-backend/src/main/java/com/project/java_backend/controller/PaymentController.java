package com.project.java_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.java_backend.containers.TicketOrderContainer;
import com.project.java_backend.model.Payment;
import com.project.java_backend.model.RegisteredUser;
import com.project.java_backend.model.Ticket;
import com.project.java_backend.service.PaymentService;
import com.project.java_backend.service.PurchaseAccountService;
import com.project.java_backend.service.PurchaseTicketService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

	@Autowired
	private PurchaseTicketService purchaseTicketService;
	@Autowired
	private PurchaseAccountService purchaseAccountService;
	@Autowired
	private PaymentService paymentService;

	// Purchase and create tickets
	@PostMapping(value="/tickets", consumes="application/json", produces="application/json")
	public ResponseEntity<List<Ticket>> purchaseTickets(@RequestBody TicketOrderContainer orderInfo) {
		List<Ticket> createdTickets = purchaseTicketService.purchaseTickets(orderInfo.getEmail(), 
																			orderInfo.getRegisteredUserId(), 
																			orderInfo.getShowtimeId(), 
																			orderInfo.getSeatIds(), 
																			orderInfo.getCardNumber(),
																			orderInfo.getPrice());
		return ResponseEntity.ok(createdTickets);
	}

	// Purchase and create account
	@PostMapping(value="/account", consumes="application/json", produces="application/json")
	public ResponseEntity<RegisteredUser> purchaseAccount(@RequestBody RegisteredUser user) {
		RegisteredUser createdUser = purchaseAccountService.purchaseAccount(user);
		return ResponseEntity.ok(createdUser);
	}

	// Get all payments
	@GetMapping
	public ResponseEntity<List<Payment>> getAllPayments() {
		return ResponseEntity.ok(paymentService.getAllPayments());
	}

	// Get payment by Id
	@GetMapping(value="/{id}", consumes="application/json", produces="application/json")
	public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
		Payment payment = paymentService.getPaymentById(id);
		return ResponseEntity.ok(payment);
	}

	// Update payment
	@PutMapping(value="/{id}", consumes="application/json", produces="application/json")
	public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment paymentDetails) {
		Payment updatedPayment = paymentService.updatePayment(id, paymentDetails.getAmount(), paymentDetails.getPaymentMethod());
		return ResponseEntity.ok(updatedPayment);
	}

	// Delete payment
	@DeleteMapping(value="/{id}", consumes="application/json", produces="application/json")
	public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
		paymentService.deletePayment(id);
		return ResponseEntity.noContent().build();
	}
 
}
