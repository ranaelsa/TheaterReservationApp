package com.project.java_backend.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.java_backend.model.RegisteredUser;

@Service
public class PurchaseAccountService extends PaymentService{

	@Autowired
	private RegisteredUserService registeredUserService;

	public RegisteredUser purchaseAccount(RegisteredUser user) {

		// Validate user inputs before processing payment
		registeredUserService.testUser(user);

		makePayment(20.0, user.getCardNumber(), user.getEmail());

		// Set lastAccountCharge for user to current time
    	user.setLastAccountCharge(LocalDateTime.now());

		return registeredUserService.createUser(user);
	}

	@Override
	protected String buildEmailReceipt(Double amount, String paymentMethod) {
        return super.buildEmailReceipt(amount, paymentMethod) + "Item purchased: Account (1 year)\n"
                                                                + "This is an annual charge\n";
    }
}
