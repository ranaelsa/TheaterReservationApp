package com.project.java_backend.service;

import com.project.java_backend.model.Showtime;
import com.project.java_backend.model.Theater;
import com.project.java_backend.model.Movie;
import com.project.java_backend.repository.ShowtimeRepository;
import com.project.java_backend.repository.TheaterRepository;
import com.project.java_backend.repository.MovieRepository;
import com.project.java_backend.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowtimeService {

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SeatAvailabilityService seatAvailabilityService;

    // Create new showtime
    public Showtime createShowtime(Long theaterId, Long movieId, Showtime showtime) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new ResourceNotFoundException("Theater not found with id " + theaterId));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id " + movieId));

        showtime.setTheater(theater);
        showtime.setMovie(movie);

        Showtime savedShowtime = showtimeRepository.save(showtime);

        // Initialize seat availability for the showtime
        seatAvailabilityService.initializeSeatAvailabilityForShowtime(savedShowtime.getId());

        return savedShowtime;
    }

    // Get showtime by ID
    public Showtime getShowtimeById(Long id) {
        return showtimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Showtime not found with id " + id));
    }

    // Get showtimes by theater ID
    public List<Showtime> getShowtimesByTheaterId(Long theaterId) {
        return showtimeRepository.findByTheaterId(theaterId);
    }

    // Get showtimes by movie ID
    public List<Showtime> getShowtimesByMovieId(Long movieId) {
        return showtimeRepository.findByMovieId(movieId);
    }

    // Update showtime
    public Showtime updateShowtime(Long id, Showtime updatedShowtime) {
        Showtime existingShowtime = getShowtimeById(id);
        existingShowtime.setStartTime(updatedShowtime.getStartTime());
        return showtimeRepository.save(existingShowtime);
    }

    // Delete showtime
    public void deleteShowtime(Long id) {
        if (!showtimeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Showtime not found with id " + id);
        }
        showtimeRepository.deleteById(id);
    }
}
