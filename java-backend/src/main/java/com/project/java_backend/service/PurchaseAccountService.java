package com.project.java_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.java_backend.model.RegisteredUser;

@Service
public class PurchaseAccountService extends PaymentService{

	@Autowired
	private RegisteredUserService registeredUserService;

	public RegisteredUser purchaseAccount(RegisteredUser user) {

		// Validate user inputs before processing payment
		if (user.getCardNumber() == null ||
			user.getExpiryDate() == null ||
			user.getCvc() == null ||
			user.getCardNumber().isBlank() || 
			user.getCardNumber().length() != 16 ||
			user.getExpiryDate().isBlank() || 
			user.getExpiryDate().length() != 4 ||
			user.getCvc().isBlank() || 
			user.getCvc().length() != 3
			) {
				throw new IllegalArgumentException("Invalid card details");
		}
		makePayment(20.0, user.getCardNumber(), user.getEmail());

		return registeredUserService.createUser(user);
	}

	@Override
	protected String buildEmailReceipt(Double amount, String paymentMethod) {
        return super.buildEmailReceipt(amount, paymentMethod) + "Item purchased: Account (1 year)\n"
                                                                + "This is an annual charge\n";
    }
}
