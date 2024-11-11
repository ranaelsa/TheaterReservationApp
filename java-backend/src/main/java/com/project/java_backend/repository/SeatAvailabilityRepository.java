package com.project.java_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.java_backend.model.SeatAvailability;
import com.project.java_backend.model.SeatAvailabilityId;

public interface SeatAvailabilityRepository extends JpaRepository<SeatAvailability, Long> {
    // Get all available seats for a showtime
    List<SeatAvailability> findByShowtimeIdAndIsAvailableTrue(Long showtimeId);
    
    // Find a specific seat availability by showtime and seat
    Optional<SeatAvailability> findByIdShowtimeIdAndSeatId(Long seatId, Long showtimeId);
    
    // Find by seatAvailability ID
    Optional<SeatAvailability> findById(SeatAvailabilityId id);

    // Count booked seats (isAvailable = false) for a showtime
    long countByIdShowtimeIdAndIsAvailableFalse(Long showtimeId);
    
    // Count total seats for a showtime
    long countByIdShowtimeId(Long showtimeId);
}