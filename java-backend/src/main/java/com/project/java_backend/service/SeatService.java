package com.project.java_backend.service;

import com.project.java_backend.model.Seat;
import com.project.java_backend.model.Theater;
import com.project.java_backend.repository.SeatRepository;
import com.project.java_backend.repository.TheaterRepository;
import com.project.java_backend.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    // Create new seat
    public Seat createSeat(Long theaterId, Seat seat) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new ResourceNotFoundException("Theater not found with id " + theaterId));
        seat.setTheater(theater);
        return seatRepository.save(seat);
    }

    // Get seat by ID
    public Seat getSeatById(Long id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found with id " + id));
    }

    // Get seats by theater ID
    public List<Seat> getSeatsByTheaterId(Long theaterId) {
        return seatRepository.findByTheaterId(theaterId);
    }

    // Update seat
    public Seat updateSeat(Long id, Seat updatedSeat) {
        Seat existingSeat = getSeatById(id);
        existingSeat.setSeatNumber(updatedSeat.getSeatNumber());
        existingSeat.setTheater(updatedSeat.getTheater());
        return seatRepository.save(existingSeat);
    }

    // Delete seat
    public void deleteSeat(Long id) {
        if (!seatRepository.existsById(id)) {
            throw new ResourceNotFoundException("Seat not found with id " + id);
        }
        seatRepository.deleteById(id);
    }
}
