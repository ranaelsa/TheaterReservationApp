package com.project.java_backend.service;

import com.project.java_backend.model.*;
import com.project.java_backend.repository.*;
import com.project.java_backend.exception.ResourceNotFoundException;
import com.project.java_backend.exception.SeatNotAvailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private RegisteredUserRepository userRepository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatAvailabilityRepository seatAvailabilityRepository;
    @Autowired
    private EmailService emailService;

    // Purchase ticket
    public Ticket purchaseTicket(Long userId, Long showtimeId, Long seatId) {
        RegisteredUser user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new ResourceNotFoundException("Showtime not found with id " + showtimeId));

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found with id " + seatId));

        // Check seat availability
        SeatAvailabilityId seatAvailabilityId = new SeatAvailabilityId(seatId, showtimeId);
        SeatAvailability seatAvailability = seatAvailabilityRepository.findById(seatAvailabilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat availability not found"));

        if (!seatAvailability.getIsAvailable()) {
            throw new SeatNotAvailableException("Seat is not available");
        }

        // Update seat availability
        seatAvailability.setIsAvailable(false);
        seatAvailabilityRepository.save(seatAvailability);

        // Create ticket
        Ticket ticket = new Ticket(
                showtime.getPrice(), // Assuming price is stored in Showtime
                LocalDateTime.now(),
                user,
                showtime,
                seat
        );

        Ticket savedTicket = ticketRepository.save(ticket);

        // Send confirmation email
        String to = user.getEmail();
        String subject = "Your Ticket Confirmation";
        String text = buildTicketConfirmationEmail(user, showtime, seat, savedTicket);

        emailService.sendSimpleEmail(to, subject, text);

        return savedTicket;
    }
    private String buildTicketConfirmationEmail(RegisteredUser user, Showtime showtime, Seat seat, Ticket ticket) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return "Hello " + user.getName() + ",\n\n"
                + "Thank you for your purchase!\n\n"
                + "Here are your ticket details:\n"
                + "Ticket ID: " + ticket.getId() + "\n"
                + "Movie: " + showtime.getMovie().getTitle() + "\n"
                + "Showtime: " + showtime.getStartTime().format(formatter) + "\n"
                + "Theater: " + showtime.getTheater().getName() + "\n"
                + "Seat: " + seat.getSeatNumber() + "\n"
                + "Ticket Price: $" + showtime.getPrice() + "\n"
                + "Purchase Time: " + ticket.getPurchaseTime().format(formatter) + "\n\n"
                + "You can use the Ticket ID to manage your reservation.\n"
                + "To cancel your ticket, please visit our website or contact customer support with your Ticket ID.\n\n"
                + "Enjoy your movie!\n"
                + "AcmePlex Ticketing Team";
    }
    // Get ticket by ID
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + id));
    }

    // Get tickets by user ID
    public List<Ticket> getTicketsByUserId(Long userId) {
        return ticketRepository.findByUserId(userId);
    }

    // Cancel ticket
    public void cancelTicket(Long id) {
        Ticket ticket = getTicketById(id);

        // Update seat availability
        SeatAvailabilityId seatAvailabilityId = new SeatAvailabilityId(ticket.getSeat().getId(),
                        ticket.getShowtime().getId());
        SeatAvailability seatAvailability = seatAvailabilityRepository.findById(seatAvailabilityId)
                        .orElseThrow(() -> new ResourceNotFoundException("Seat availability not found"));

        seatAvailability.setIsAvailable(true);
        seatAvailabilityRepository.save(seatAvailability);

        // Delete ticket
        ticketRepository.deleteById(id);
        // Send cancellation email
        String to = ticket.getUser().getEmail();
        String subject = "Your Ticket Cancellation";
        String text = buildTicketCancellationEmail(ticket);

        emailService.sendSimpleEmail(to, subject, text);
    }
    private String buildTicketCancellationEmail(Ticket ticket) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return "Hello " + ticket.getUser().getName() + ",\n\n"
                + "Your ticket with Ticket ID " + ticket.getId() + " has been successfully canceled.\n\n"
                + "Canceled Ticket Details:\n"
                + "Movie: " + ticket.getShowtime().getMovie().getTitle() + "\n"
                + "Showtime: " + ticket.getShowtime().getStartTime().format(formatter) + "\n"
                + "Theater: " + ticket.getShowtime().getTheater().getName() + "\n"
                + "Seat: " + ticket.getSeat().getSeatNumber() + "\n"
                + "Original Purchase Time: " + ticket.getPurchaseTime().format(formatter) + "\n\n"
                + "We're sorry to see you cancel. We hope to serve you again soon.\n"
                + "AcmePlex Ticketing Team";
    }
}
