package com.project.java_backend.service;

import com.project.java_backend.model.SeatAvailability;
import com.project.java_backend.model.SeatAvailabilityId;
import com.project.java_backend.model.Seat;
import com.project.java_backend.model.Showtime;
import com.project.java_backend.repository.SeatAvailabilityRepository;
import com.project.java_backend.repository.SeatRepository;
import com.project.java_backend.repository.ShowtimeRepository;
import com.project.java_backend.exception.ResourceNotFoundException;

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

        List<Seat> seats = seatRepository.findByTheaterId(showtime.getTheater().getId());

        for (Seat seat : seats) {
            SeatAvailability seatAvailability = new SeatAvailability(seat, showtime, true);
            seatAvailabilityRepository.save(seatAvailability);
        }
    }

    // Get available seats for a showtime
    public List<SeatAvailability> getAvailableSeatsByShowtimeId(Long showtimeId) {
        return seatAvailabilityRepository.findByShowtimeIdAndIsAvailableTrue(showtimeId);
    }

    // Get seat availability
    public SeatAvailability getSeatAvailability(Long seatId, Long showtimeId) {
        SeatAvailabilityId id = new SeatAvailabilityId(seatId, showtimeId);
        return seatAvailabilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat availability not found"));
    }

    // Update seat availability
    public SeatAvailability updateSeatAvailability(SeatAvailabilityId id, SeatAvailability seatAvailability) {
        SeatAvailability existingSeatAvailability = getSeatAvailability(id.getSeatId(), id.getShowtimeId());
        existingSeatAvailability.setIsAvailable(seatAvailability.getIsAvailable());
        return seatAvailabilityRepository.save(existingSeatAvailability);
    }
}
