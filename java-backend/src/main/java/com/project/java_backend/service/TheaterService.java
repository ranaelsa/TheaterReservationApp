package com.project.java_backend.service;

import com.project.java_backend.model.Theater;
import com.project.java_backend.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheaterService {

    private final TheaterRepository theaterRepository;

    @Autowired
    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    // Get all theaters
    public List<Theater> getAllTheaters() {
        return theaterRepository.findAll();
    }

    // Get theater by ID
    public Optional<Theater> getTheaterById(Long id) {
        return theaterRepository.findById(id);
    }

    // Create new theater
    public Theater createTheater(Theater theater) {
        // Business logic can be added here (e.g., validation)
        return theaterRepository.save(theater);
    }

    // Update existing theater
    public Theater updateTheater(Long id, Theater theaterDetails) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Theater not found"));

        theater.setName(theaterDetails.getName());
        theater.setLocation(theaterDetails.getLocation());
        // Update other fields if necessary

        return theaterRepository.save(theater);
    }

    // Delete theater by ID
    public void deleteTheater(Long id) {
        if (!theaterRepository.existsById(id)) {
            throw new IllegalArgumentException("Theater not found");
        }
        theaterRepository.deleteById(id);
    }
}
