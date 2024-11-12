package com.project.java_backend.controller;

import com.project.java_backend.model.Theater;
import com.project.java_backend.service.TheaterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theaters")
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    // Get all theaters
    @GetMapping
    public List<Theater> getAllTheaters() {
        return theaterService.getAllTheaters();
    }

    // Get theater by ID
    @GetMapping(value="/{id}", consumes="application/json", produces="application/json")
    public ResponseEntity<Theater> getTheaterById(@PathVariable Long id) {
        Theater theater = theaterService.getTheaterById(id);
        return ResponseEntity.ok(theater);
    }

    // Create new theater
    @PostMapping(consumes="application/json", produces="application/json")
    public ResponseEntity<Theater> createTheater(@RequestBody Theater theater) {
        int numRows = 6;
        int seatsPerRow = 11;
        Theater createdTheater = theaterService.createTheater(theater, numRows, seatsPerRow);
        return ResponseEntity.ok(createdTheater);
    }

    // Update theater
    @PutMapping(value="/{id}", consumes="application/json", produces="application/json")
    public ResponseEntity<Theater> updateTheater(@PathVariable Long id, @RequestBody Theater theaterDetails) {
        Theater updatedTheater = theaterService.updateTheater(id, theaterDetails);
        return ResponseEntity.ok(updatedTheater);
    }

    // Delete theater
    @DeleteMapping(value="/{id}", consumes="application/json", produces="application/json")
    public ResponseEntity<Void> deleteTheater(@PathVariable Long id) {
        theaterService.deleteTheater(id);
        return ResponseEntity.noContent().build();
    }
}
