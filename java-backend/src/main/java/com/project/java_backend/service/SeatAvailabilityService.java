package com.project.java_backend.service;

import com.project.java_backend.model.SeatAvailability;
import com.project.java_backend.model.SeatAvailabilityId;
import com.project.java_backend.model.Seat;
import com.project.java_backend.model.Showtime;
import com.project.java_backend.repository.SeatAvailabilityRepository;
import com.project.java_backend.repository.SeatRepository;
import com.project.java_backend.repository.ShowtimeRepository;
import com.project.java_backend.exception.ResourceNotFoundException;
import com.project.java_backend.exception.SeatNotAvailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatAvailabilityService {

    @Autowired
    private SeatAvailabilityRepository seatAvailabilityRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    // Initialize seat availability for a new showtime
    public void initializeSeatAvailabilityForShowtime(Long showtimeId) {
        Showtime showtime = showtimeRepository.findById(showtimeId)
            .orElseThrow(() -> new ResourceNotFoundException("Showtime not found with id " + showtimeId));
        // Get all seats in the theater associated with the showtime
        List<Seat> seats = seatRepository.findByTheaterId(showtime.getTheater().getId());

        // Create a SeatAvailability entry for each seat in the theater
        for (Seat seat : seats) {
            SeatAvailability seatAvailability = new SeatAvailability(seat, showtime, true);
            seatAvailabilityRepository.save(seatAvailability);
        }
    }

    // Get list of available seats for a showtime
    public List<SeatAvailability> getAvailableSeatsByShowtimeId(Long showtimeId) {
        return seatAvailabilityRepository.findByShowtimeIdAndIsAvailableTrue(showtimeId);
    }

    // Check if a specific seat is available for a showtime
    public boolean isSeatAvailable(Long seatId, Long showtimeId) {
        return seatAvailabilityRepository.findByIdShowtimeIdAndSeatId(showtimeId, seatId)
                .map(SeatAvailability::getIsAvailable)
                .orElse(false); // Return false if seat availability record does not exist
    }

    // Returns a seat availability object
    public SeatAvailability getSeatAvailability(Long seatId, Long showtimeId) {
        SeatAvailabilityId id = new SeatAvailabilityId(seatId, showtimeId);
        return seatAvailabilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat availability not found"));
    }

    // Reserve a specific seat for a showtime
    public SeatAvailability reserveSeat(Long seatId, Long showtimeId) {
        SeatAvailability seatAvailability = getSeatAvailability(seatId, showtimeId);

        if (!seatAvailability.getIsAvailable()) {
            throw new SeatNotAvailableException("Seat is already reserved");
        }

        // Handle 10% limit on advance seat sales
        Showtime showtime = showtimeRepository.findById(showtimeId)
            .orElseThrow(() -> new ResourceNotFoundException("Showtime not found with id " + showtimeId));
        if (!showtime.getMovie().isPublic() && isTenPercentOrMoreBooked(showtimeId)) {
            throw new IllegalStateException("Only 10% of seats can be booked by registered users before public release.");
        }
        // Set seat to unavailable and save
        seatAvailability.setIsAvailable(false);
        return seatAvailabilityRepository.save(seatAvailability);
    }

    // Release a reserved seat (e.g., after ticket cancellation)
    public SeatAvailability releaseSeat(Long seatId, Long showtimeId) {
        SeatAvailability seatAvailability = getSeatAvailability(seatId, showtimeId);

        // Set seat to available and save
        seatAvailability.setIsAvailable(true);
        return seatAvailabilityRepository.save(seatAvailability);
    }

    // Check if 10% or more seats are booked for a showtime (to check when registered users book seats for a non-public movie)
    public boolean isTenPercentOrMoreBooked(Long showtimeId) {
        long totalSeats = seatAvailabilityRepository.countByIdShowtimeId(showtimeId);
        long bookedSeats = seatAvailabilityRepository.countByIdShowtimeIdAndIsAvailableFalse(showtimeId);

        // Calculate 10% of total seats
        double tenPercentThreshold = totalSeats * 0.10;

        // Check if booked seats are equal to or exceed 10% threshold
        return bookedSeats >= tenPercentThreshold;
    }
}
