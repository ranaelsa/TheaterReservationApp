package com.project.java_backend.service;

import com.project.java_backend.model.Movie;
import com.project.java_backend.model.RegisteredUser;
import com.project.java_backend.repository.MovieRepository;
import com.project.java_backend.repository.RegisteredUserRepository;
import com.project.java_backend.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Autowired
    private EmailService emailService;

    // Create a new movie and notify registered users
    public Movie createMovie(Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
        
        // Notify registered users of the new movie
        notifyRegisteredUsers(savedMovie);

        return savedMovie;
    }

    // Method to notify registered users
    private void notifyRegisteredUsers(Movie movie) {
        List<RegisteredUser> registeredUsers = registeredUserRepository.findAll(); // retrieves all registered users
        String subject = "New Movie Available: " + movie.getTitle();
        String text = """
                      Dear Registered User,
                      
                      We're excited to announce a new movie has been added to AcmePlex!
                      
                      Movie Title: """ + " " + movie.getTitle() + "\n" +
                "Description: " + movie.getDescription() + "\n" +
                "Rating: " + movie.getRating() + "\n\n" +
                "Log in now to see more details and book your tickets!\n\n" +
                "Best regards,\nAcmePlex Ticketing Team";

        for (RegisteredUser user : registeredUsers) {
            emailService.sendSimpleEmail(user.getEmail(), subject, text);
        }
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
        Movie movie = getMovieById(id);

        if(updatedMovie.getTitle() != null && !updatedMovie.getTitle().isBlank()){movie.setTitle(updatedMovie.getTitle());}
        if(updatedMovie.getDescription() != null && !updatedMovie.getDescription().isBlank()){movie.setDescription(updatedMovie.getDescription());}
        if(updatedMovie.getDuration() != 0){movie.setDuration(updatedMovie.getDuration());}
        if(updatedMovie.getRating() != null && !updatedMovie.getRating().isBlank()){movie.setRating(updatedMovie.getRating());}

        return movieRepository.save(movie);
    }

    // Delete movie
    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Movie not found with id " + id);
        }
        movieRepository.deleteById(id);
    }
}
