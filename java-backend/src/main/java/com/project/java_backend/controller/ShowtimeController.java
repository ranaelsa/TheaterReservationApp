package com.project.java_backend.controller;

import com.project.java_backend.model.Showtime;
import com.project.java_backend.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeController {

    @Autowired
    private ShowtimeService showtimeService;

    // Create a new showtime
    @PostMapping(value = "/create/{theaterId}/{movieId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Showtime> createShowtime(@PathVariable Long theaterId,
                                                   @PathVariable Long movieId,
                                                   @RequestBody Showtime showtime) {
        Showtime createdShowtime = showtimeService.createShowtime(theaterId, movieId, showtime);
        return ResponseEntity.ok(createdShowtime);
    }

    // Get showtime by ID
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Showtime> getShowtimeById(@PathVariable Long id) {
        Showtime showtime = showtimeService.getShowtimeById(id);
        return ResponseEntity.ok(showtime);
    }

    // Get showtimes by theater ID
    @GetMapping(value = "/theater/{theaterId}", produces = "application/json")
    public ResponseEntity<List<Showtime>> getShowtimesByTheaterId(@PathVariable Long theaterId) {
        List<Showtime> showtimes = showtimeService.getShowtimesByTheaterId(theaterId);
        return ResponseEntity.ok(showtimes);
    }

    // Get showtimes by movie ID
    @GetMapping(value = "/movie/{movieId}", produces = "application/json")
    public ResponseEntity<List<Showtime>> getShowtimesByMovieId(@PathVariable Long movieId) {
        List<Showtime> showtimes = showtimeService.getShowtimesByMovieId(movieId);
        return ResponseEntity.ok(showtimes);
    }

    @GetMapping(value = "/movieandtheater/{movieId}/{theaterId}", produces = "application/json")
    public ResponseEntity<List<Showtime>> getShowtimesByMovieAndTheater(@PathVariable Long movieId, @PathVariable Long theaterId) {
        List<Showtime> showtimes = showtimeService.getShowtimesByMovieAndTheater(movieId, theaterId);
        return ResponseEntity.ok(showtimes);
    }

    // Update an existing showtime
    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Showtime> updateShowtime(@PathVariable Long id,
                                                   @RequestBody Showtime updatedShowtime) {
        Showtime showtime = showtimeService.updateShowtime(id, updatedShowtime);
        return ResponseEntity.ok(showtime);
    }

    // Delete a showtime by ID
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> deleteShowtime(@PathVariable Long id) {
        showtimeService.deleteShowtime(id);
        return ResponseEntity.noContent().build();
    }
}
