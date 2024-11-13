package com.project.java_backend.service;

import com.project.java_backend.model.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseTicketService extends PaymentService{

    @Autowired
    private TicketService ticketService;

    @Autowired
    private SeatAvailabilityService seatAvailabilityService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private ShowtimeService showtimeService;

    // Purchase 1 or more tickets for a single showtime
    public List<Ticket> purchaseTickets(String email, RegisteredUser user, Long showtimeId, List<Long> seatIds, String cardNumber) {

        // Calculate the total cost of tickets
        Double price = 0.0;
        for (int i = 0; i < seatIds.size(); i++) {
            price += seatService.getSeatById(seatIds.get(i)).getPrice();
        }

        // Make payment before continuing
        if (cardNumber == null) {
            //For payment covered completely by coupon
            makePayment(price, email);
        } else {
            makePayment(price, email, cardNumber);
        }

        // Reserve the seats and create tickets
        List<Ticket> tickets = new ArrayList<Ticket>();
        for (int i = 0; i < seatIds.size(); i++) {
            seatAvailabilityService.reserveSeat(seatIds.get(i), showtimeId);
            tickets.add(ticketService.createTicket(seatService.getSeatById(seatIds.get(i)).getPrice(), 
                                                    email, 
                                                    user, 
                                                    showtimeService.getShowtimeById(showtimeId), 
                                                    seatService.getSeatById(seatIds.get(i))));
        }

        // Send tickets via email
        emailService.sendSimpleEmail(email, "Your Tickets", buildTicketEmail(tickets));

        return tickets;
    }

    @Override
    protected String buildEmailReceipt(Double amount, String paymentMethod) {
        return super.buildEmailReceipt(amount, paymentMethod) + "Item purchased: Ticket\n"
                                                                + "You will receive your tickets by email shortly\n";
    }

    private String buildTicketEmail(List<Ticket> tickets) {
        String emailbody = "Your tickets are attached below. Enjoy!\n\n" +
                            "Showtime: " + tickets.get(0).getShowtime().getStartTime().toString() + "\n" +
                            "Theater: " + tickets.get(0).getSeat().getTheater().getName() + "\n";
        for (int i = 0; i < tickets.size(); i++) {
            emailbody = emailbody.concat(
                "Ticket " + i + "\n" +
                "\tTicket ID: " + tickets.get(i).getId().toString() + "\n" +
                "\tSeat: " + tickets.get(i).getSeat().getSeatNumber() + "\n"
            );
        } 
        return emailbody;
    }
}
