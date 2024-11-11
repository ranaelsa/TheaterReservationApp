package com.project.java_backend.service;

import com.project.java_backend.model.Movie;
import com.project.java_backend.repository.MovieRepository;
import com.project.java_backend.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    // Create new movie
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    // Get movie by ID
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id " + id));
    }

    // Retrieve all public movies (for ordinary users)
    public List<Movie> getPublicMovies() {
        return movieRepository.findByIsPublic(true);
    }

    // Get all movies (for registered users)
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // Update the movie's public status
    public Movie makeMoviePublic(Long id) {
        Movie movie = getMovieById(id);
        movie.setPublic(true);
        return movieRepository.save(movie);
    }

    // Update movie
    public Movie updateMovie(Long id, Movie updatedMovie) {
        Movie existingMovie = getMovieById(id);

        existingMovie.setTitle(updatedMovie.getTitle());
        existingMovie.setDescription(updatedMovie.getDescription());
        existingMovie.setDuration(updatedMovie.getDuration());
        existingMovie.setRating(updatedMovie.getRating());

        return movieRepository.save(existingMovie);
    }

    // Delete movie
    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Movie not found with id " + id);
        }
        movieRepository.deleteById(id);
    }
}
