package com.project.java_backend.service;

import com.project.java_backend.model.*;
import com.project.java_backend.repository.*;
import com.project.java_backend.exception.ResourceNotFoundException;
import com.project.java_backend.exception.SeatNotAvailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        return ticketRepository.save(ticket);
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
        SeatAvailabilityId seatAvailabilityId = new SeatAvailabilityId(ticket.getSeat().getId(), ticket.getShowtime().getId());
        SeatAvailability seatAvailability = seatAvailabilityRepository.findById(seatAvailabilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat availability not found"));

        seatAvailability.setIsAvailable(true);
        seatAvailabilityRepository.save(seatAvailability);

        // Delete ticket
        ticketRepository.deleteById(id);
    }
}
