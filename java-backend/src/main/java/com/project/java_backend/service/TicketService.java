package com.project.java_backend.service;

import com.project.java_backend.model.*;
import com.project.java_backend.repository.TicketRepository;
import com.project.java_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SeatAvailabilityService seatAvailabilityService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private EmailService emailService;

    // Create a new ticket
    // Assumes coupon redemption handled in controller
    public Ticket createTicket(Double price, String paymentMethod, Showtime showtime, Seat seat, String email, RegisteredUser user) {
        // Reserve seat
        seatAvailabilityService.reserveSeat(seat.getId(), showtime.getId());

        // Process payment
        paymentService.createPayment(price, paymentMethod);

        // Save ticket
        Ticket ticket = new Ticket(price, LocalDateTime.now(), email, user, showtime, seat); // User can be null for non-registered
        Ticket savedTicket = ticketRepository.save(ticket);

        // Send purchase confirmation email
        emailService.sendSimpleEmail(email, "Ticket Confirmation", buildPurchaseEmail(ticket));

        return savedTicket;
    }

    private String buildPurchaseEmail(Ticket ticket) {
        return "Thank you for your purchase!\n\n" +
               "Ticket ID: " + ticket.getId() + "\n" +
               "Showtime: " + ticket.getShowtime().getStartTime() + "\n" +
               "Seat: " + ticket.getSeat().getSeatNumber() + "\n" +
               "Price: $" + ticket.getPrice() + "\n\n" +
               "Enjoy your movie!";
    }

    // Cancel ticket and issue a coupon
    public void cancelTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + id));

        // Check if cancellation is within 72-hour limit
        LocalDateTime showtimeDate = ticket.getShowtime().getStartTime();
        if (showtimeDate.isBefore(LocalDateTime.now().plusHours(72))) {
            throw new IllegalStateException("Cannot cancel ticket within 72 hours of showtime.");
        }

        // Release seat
        seatAvailabilityService.releaseSeat(ticket.getSeat().getId(), ticket.getShowtime().getId());

        // Issue coupon
        Double couponAmount = (ticket.getUser() != null) ? ticket.getPrice() : ticket.getPrice() * 0.85; // 15% fee for ordinary users
        Coupon coupon = couponService.createCoupon(couponAmount);

        // Send cancellation email with coupon
        String cancellationEmail = buildCancellationEmail(ticket, coupon);
        emailService.sendSimpleEmail(ticket.getEmail(), "Ticket Cancellation", cancellationEmail);

        // Delete ticket
        ticketRepository.deleteById(id);
    }

    private String buildCancellationEmail(Ticket ticket, Coupon coupon) {
        return "Your ticket has been successfully canceled.\n\n" +
               "Ticket ID: " + ticket.getId() + "\n" +
               "Showtime: " + ticket.getShowtime().getStartTime() + "\n" +
               "Seat: " + ticket.getSeat().getSeatNumber() + "\n" +
               "Refund Amount: $" + coupon.getAmount() + "\n\n" +
               "Coupon Code: " + coupon.getCouponCode() + "\n" +
               "Expiration Date: " + coupon.getExpirationDate() + "\n" +
               "Thank you for choosing AcmePlex!";
    }
}
