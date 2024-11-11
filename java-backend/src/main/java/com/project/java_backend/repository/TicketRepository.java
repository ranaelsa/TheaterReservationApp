package com.project.java_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.java_backend.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Find a ticket by showtime and seat, which will help check for duplicate bookings
    Optional<Ticket> findByShowtimeIdAndSeatId(Long showtimeId, Long seatId);
    
    // Find tickets for a specific user (registered users only)
    List<Ticket> findByUserId(Long userId);
}