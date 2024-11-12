package com.project.java_backend.controller;

import com.project.java_backend.model.SeatAvailability;
import com.project.java_backend.service.SeatAvailabilityService;
import com.project.java_backend.exception.SeatNotAvailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seat-availability")
public class SeatAvailabilityController {

    @Autowired
    private SeatAvailabilityService seatAvailabilityService;

    // Get available seats for a specific showtime
    @GetMapping(value = "/available/{showtimeId}", produces = "application/json")
    public ResponseEntity<List<SeatAvailability>> getAvailableSeatsByShowtimeId(@PathVariable Long showtimeId) {
        List<SeatAvailability> availableSeats = seatAvailabilityService.getAvailableSeatsByShowtimeId(showtimeId);
        return ResponseEntity.ok(availableSeats);
    }

    // Check if a specific seat is available for a showtime
    @GetMapping(value = "/check/{showtimeId}/{seatId}", produces = "application/json")
    public ResponseEntity<Boolean> isSeatAvailable(@PathVariable Long showtimeId, @PathVariable Long seatId) {
        boolean isAvailable = seatAvailabilityService.isSeatAvailable(seatId, showtimeId);
        return ResponseEntity.ok(isAvailable);
    }

    // Reserve a specific seat for a showtime
    @PostMapping(value = "/reserve/{showtimeId}/{seatId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SeatAvailability> reserveSeat(@PathVariable Long showtimeId, @PathVariable Long seatId) {
        try {
            SeatAvailability reservedSeat = seatAvailabilityService.reserveSeat(seatId, showtimeId);
            return ResponseEntity.ok(reservedSeat);
        } catch (SeatNotAvailableException e) {
            return ResponseEntity.status(409).body(null); // Conflict status if the seat is already reserved
        }
    }

    // Release a reserved seat (e.g., after ticket cancellation)
    @PostMapping(value = "/release/{showtimeId}/{seatId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SeatAvailability> releaseSeat(@PathVariable Long showtimeId, @PathVariable Long seatId) {
        SeatAvailability releasedSeat = seatAvailabilityService.releaseSeat(seatId, showtimeId);
        return ResponseEntity.ok(releasedSeat);
    }
}
