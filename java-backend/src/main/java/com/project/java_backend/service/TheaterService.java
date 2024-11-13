package com.project.java_backend.service;

import com.project.java_backend.model.Seat;
import com.project.java_backend.model.Theater;
import com.project.java_backend.repository.TheaterRepository;
import com.project.java_backend.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private SeatService seatService;

    // Create new theater with automatic seat generation
    public Theater createTheater(Theater theater, int numRows, int seatsPerRow) {
        // Save the theater first
        Theater savedTheater = theaterRepository.save(theater);

        // Automatically create seats with lettered rows
        for (int row = 0; row < numRows; row++) {
            char rowLetter = (char) ('A' + row); // Convert row number to letter (A, B, C, ...)
            for (int seatNumber = 1; seatNumber <= seatsPerRow; seatNumber++) {
                Seat seat = new Seat();
                seat.setSeatNumber(rowLetter + String.valueOf(seatNumber)); // e.g., "A1", "B1"
                seatService.createSeat(savedTheater.getId(), seat);
            }
        }

        return savedTheater;
    }

    // Get theater by ID
    public Theater getTheaterById(Long id) {
        return theaterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theater not found with id " + id));
    }

    // Get all theaters
    public List<Theater> getAllTheaters() {
        return theaterRepository.findAll();
    }

    // Update theater
    public Theater updateTheater(Long id, Theater updatedTheater) {
        Theater existingTheater = getTheaterById(id);

        existingTheater.setName(updatedTheater.getName());
        existingTheater.setLocation(updatedTheater.getLocation());

        return theaterRepository.save(existingTheater);
    }

    // Delete theater
    public void deleteTheater(Long id) {
        if (!theaterRepository.existsById(id)) {
            throw new ResourceNotFoundException("Theater not found with id " + id);
        }
        theaterRepository.deleteById(id);
    }
}
