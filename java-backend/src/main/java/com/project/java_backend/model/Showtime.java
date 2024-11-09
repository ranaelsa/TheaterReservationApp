package com.project.java_backend.model;

import jakarta.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "showtimes",
       uniqueConstraints = @UniqueConstraint(columnNames = {"theater_id", "movie_id", "start_time"}))
public class Showtime {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    // Relationships
    @ManyToOne(optional = false)
    @JoinColumn(name = "theater_id", nullable = false)
    @JsonBackReference
    private Theater theater;

    @ManyToOne(optional = false)
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonBackReference
    private Movie movie;

    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SeatAvailability> seatAvailabilities;

    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ticket> tickets;

    // Constructors
    public Showtime() {
        // Default constructor
    }

    public Showtime(LocalDateTime startTime, Theater theater, Movie movie) {
        this.startTime = startTime;
        this.theater = theater;
        this.movie = movie;
    }

    // ID
    public Long getId() {
        return id;
    }

    // Start Time
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    // Theater
    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    // Movie
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    // Seat Availabilities
    public Set<SeatAvailability> getSeatAvailabilities() {
        return seatAvailabilities;
    }

    public void setSeatAvailabilities(Set<SeatAvailability> seatAvailabilities) {
        this.seatAvailabilities = seatAvailabilities;
    }

    // Tickets
    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

}
