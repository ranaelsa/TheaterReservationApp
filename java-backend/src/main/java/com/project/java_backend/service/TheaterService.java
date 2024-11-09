package com.project.java_backend.service;

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

    // Create new theater
    public Theater createTheater(Theater theater) {
        return theaterRepository.save(theater);
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
