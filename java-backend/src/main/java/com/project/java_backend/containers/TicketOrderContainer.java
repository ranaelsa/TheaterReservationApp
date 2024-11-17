package com.project.java_backend.containers;

import java.util.List;

import com.project.java_backend.model.RegisteredUser;

// Container for sending ticket order info via JSON
public class TicketOrderContainer {
	private RegisteredUser registeredUser;
	private String email;
	private String cardNumber;
	private Long showtimeId;
	private List<Long> seatIds;

	public TicketOrderContainer() {
		// Default constructor
	}

	// Getters and setters

	public RegisteredUser getRegisteredUser() {
		return registeredUser;
	}

	public void setRegisteredUser(RegisteredUser registeredUser) {
		this.registeredUser = registeredUser;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Long getShowtimeId() {
		return showtimeId;
	}

	public void setShowtimeId(Long showtimeId) {
		this.showtimeId = showtimeId;
	}

	public List<Long> getSeatIds() {
		return seatIds;
	}

	public void setSeatIds(List<Long> seatIds) {
		this.seatIds = seatIds;
	}
}
