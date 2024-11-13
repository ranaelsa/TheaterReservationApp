package com.project.java_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.java_backend.model.RegisteredUser;

@Service
public class PurchaseAccountService extends PaymentService{

	@Autowired
	private RegisteredUserService registeredUserService;

	public RegisteredUser purchaseAccount(RegisteredUser user) {

		makePayment(20.0, user.getCardNumber(), user.getEmail());

		registeredUserService.createUser(user);

		return user;
	}

	@Override
	protected String buildEmailReceipt(Double amount, String paymentMethod) {
        return super.buildEmailReceipt(amount, paymentMethod) + "Item purchased: Account (1 year)\n"
                                                                + "This is an annual charge\n";
    }
}
