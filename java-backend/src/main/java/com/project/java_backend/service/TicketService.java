package com.project.java_backend.service;

import com.project.java_backend.model.*;
import com.project.java_backend.repository.TicketRepository;
import com.project.java_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private SeatService seatService;

    // Create a new ticket (used by PurchaseTicketService to finalize purchase)
    public Ticket createTicket(Double price, String email, RegisteredUser user, Showtime showtime, Seat seat) {
        Ticket ticket = new Ticket(price, email, user, showtime, seat);
        return ticketRepository.save(ticket);
    }

    // Get cost of tickets
    public Double getCost(List<Long> seatIds) {
        Double price = 0.0;
        for (int i = 0; i < seatIds.size(); i++) {
            price += seatService.getSeatById(seatIds.get(i)).getPrice();
        }
        return price;
    }

    // Cancel ticket and issue a coupon
    public void cancelTicket(String  code) {
        Ticket ticket = ticketRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with code " + code));

        validateCancellationTime(ticket);

        // Release seat
        seatAvailabilityService.releaseSeat(ticket.getSeat().getId(), ticket.getShowtime().getId());

        // Issue coupon and calculate refund
        Double couponAmount = calculateRefundAmount(ticket);
        Coupon coupon = couponService.createCoupon(couponAmount);

        // Send cancellation email with coupon
        emailService.sendSimpleEmail(ticket.getEmail(), "Ticket Cancellation", buildCancellationEmail(ticket, coupon));

        // Delete ticket
        ticketRepository.deleteByCode(code);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        return "Your ticket has been successfully canceled.\n\n" +
                "Ticket Code: " + ticket.getCode() + "\n" +
                "Showtime: " + ticket.getShowtime().getStartTime().format(formatter) + "\n" +
                "Seat: " + ticket.getSeat().getSeatNumber() + "\n" +
                "Refund Amount: $" + String.format("%.2f", coupon.getAmount()) + "\n\n" +
                "Coupon Code: " + coupon.getCouponCode() + "\n" +
                "Expiration Date: " + coupon.getExpirationDate().format(formatter) + "\n" +
                "Thank you for choosing AcmePlex!";
    }
    // Retrieve a single ticket by ID
    public Optional<Ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
    }

    // Retrieve all tickets (useful for admin purposes)
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // Retrieve tickets for a specific user by user ID
    public List<Ticket> getTicketsByUserId(Long userId) {
        return ticketRepository.findByUserId(userId);
    }
}
