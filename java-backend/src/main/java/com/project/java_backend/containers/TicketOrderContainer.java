package com.project.java_backend.containers;

import java.util.List;

import com.project.java_backend.model.RegisteredUser;
import com.project.java_backend.model.Seat;
import com.project.java_backend.model.Showtime;

public class TicketOrderContainer {
	private RegisteredUser registeredUser;
	private String email;
	private String cardNumber;
	private Showtime showtime;
	private List<Seat> seats;

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

	public Showtime getShowtime() {
		return showtime;
	}

	public void setShowtime(Showtime showtime) {
		this.showtime = showtime;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
}
