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
    private CouponService couponService;

    @Autowired
    private EmailService emailService;

    // Create a new ticket (used by PurchaseTicketService to finalize purchase)
    public Ticket createTicket(Double price, String email, RegisteredUser user, Showtime showtime, Seat seat) {
        Ticket ticket = new Ticket(price, email, user, showtime, seat);
        return ticketRepository.save(ticket);
    }

    // Cancel ticket and issue a coupon
    public void cancelTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + id));

        validateCancellationTime(ticket);

        // Release seat
        seatAvailabilityService.releaseSeat(ticket.getSeat().getId(), ticket.getShowtime().getId());

        // Issue coupon and calculate refund
        Double couponAmount = calculateRefundAmount(ticket);
        Coupon coupon = couponService.createCoupon(couponAmount);

        // Send cancellation email with coupon
        emailService.sendSimpleEmail(ticket.getEmail(), "Ticket Cancellation", buildCancellationEmail(ticket, coupon));

        // Delete ticket
        ticketRepository.deleteById(id);
    }

    private void validateCancellationTime(Ticket ticket) {
        LocalDateTime showtimeDate = ticket.getShowtime().getStartTime();
        if (showtimeDate.isBefore(LocalDateTime.now().plusHours(72))) {
            throw new IllegalStateException("Cannot cancel ticket within 72 hours of showtime.");
        }
    }

    private Double calculateRefundAmount(Ticket ticket) {
        return (ticket.getUser() != null) ? ticket.getPrice() : ticket.getPrice() * 0.85; // 15% fee for ordinary users
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
