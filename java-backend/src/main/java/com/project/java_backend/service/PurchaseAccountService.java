package com.project.java_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.java_backend.model.RegisteredUser;
import com.project.java_backend.model.Seat;
import com.project.java_backend.model.Showtime;

@Service
public class PurchaseAccountService extends PaymentService{

	@Autowired
	private RegisteredUserService registeredUserService;

	public RegisteredUser purchaseAccount(RegisteredUser user) {

		makePayment(20.0, Long.toString(user.getCardNumber()), user.getEmail());

		registeredUserService.createUser(user);

		

		return user;
	}
}
