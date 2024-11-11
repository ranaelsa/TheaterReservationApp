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
    public Ticket createTicket(Double price, String paymentMethod, Showtime showtime, Seat seat, String email, RegisteredUser user, String couponCode) {
        // Check 10% restriction for RUs on non-public movies
        if (!showtime.getMovie().isPublic() && user != null && !canRegisterUserBook(showtime.getId())) {
            throw new IllegalStateException("Only 10% of seats can be booked by registered users before public release.");
        }

        // Redeem coupon if provided
        if (couponCode != null) {
            price = applyCouponDiscount(couponCode, price);
        }

        // Reserve seat
        seatAvailabilityService.reserveSeat(seat.getId(), showtime.getId());

        // Process payment
        paymentService.createPayment(price, paymentMethod);

        // Save ticket
        Ticket ticket = new Ticket(price, email, user, showtime, seat);
        Ticket savedTicket = ticketRepository.save(ticket);

        // Send purchase confirmation email
        emailService.sendSimpleEmail(email, "Ticket Confirmation", buildPurchaseEmail(ticket));

        return savedTicket;
    }

    // Check if RU can book for non-public movies (10% rule)
    private boolean canRegisterUserBook(Long showtimeId) {
        return !seatAvailabilityService.isTenPercentOrMoreBooked(showtimeId);
    }

    // Apply coupon discount
    private Double applyCouponDiscount(String couponCode, Double price) {
        if (couponService.isCouponExpired(couponCode)) {
            throw new IllegalArgumentException("Coupon is expired");
        }
        Coupon coupon = couponService.redeemCoupon(couponCode);
        return price - coupon.getAmount();
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
