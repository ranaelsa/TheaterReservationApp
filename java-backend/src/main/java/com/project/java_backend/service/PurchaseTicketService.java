package com.project.java_backend.service;

import com.project.java_backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseTicketService {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private SeatAvailabilityService seatAvailabilityService;

    @Autowired
    private EmailService emailService;

    public Ticket finalizePurchase(Double price, String email, RegisteredUser user, Showtime showtime, Seat seat) {
        // Enforce booking restrictions for registered users and non-public movies
        enforceBookingRestrictions(showtime, user);

        // Reserve the seat
        seatAvailabilityService.reserveSeat(seat.getId(), showtime.getId());

        // Create the ticket
        Ticket ticket = ticketService.createTicket(price, email, user, showtime, seat);

        // Send purchase confirmation email
        emailService.sendSimpleEmail(email, "Ticket Confirmation", buildPurchaseEmail(ticket));

        return ticket;
    }

    private void enforceBookingRestrictions(Showtime showtime, RegisteredUser user) {
        if (!showtime.getMovie().isPublic() && user != null && seatAvailabilityService.isTenPercentOrMoreBooked(showtime.getId())) {
            throw new IllegalStateException("Only 10% of seats can be booked by registered users before public release.");
        }
    }

    private String buildPurchaseEmail(Ticket ticket) {
        return "Thank you for your purchase!\n\n" +
               "Ticket ID: " + ticket.getId() + "\n" +
               "Showtime: " + ticket.getShowtime().getStartTime() + "\n" +
               "Seat: " + ticket.getSeat().getSeatNumber() + "\n" +
               "Price: $" + ticket.getPrice() + "\n\n" +
               "Enjoy your movie!";
    }
}
