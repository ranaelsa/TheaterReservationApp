package com.project.java_backend.model;

import jakarta.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "seat_availability")
public class SeatAvailability implements Serializable {

    private static final long serialVersionUID = 1L;

    // Composite Primary Key
    @EmbeddedId
    private SeatAvailabilityId id;

    // Relationships
    @ManyToOne(optional = false)
    @MapsId("seatId")
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne(optional = false)
    @MapsId("showtimeId")
    @JoinColumn(name = "showtime_id", nullable = false)
    private Showtime showtime;

    // Fields
    @NotNull(message = "Availability status is required")
    private Boolean isAvailable;

    // Constructors
    public SeatAvailability() {
        // Default constructor
    }

    public SeatAvailability(Seat seat, Showtime showtime, Boolean isAvailable) {
        this.seat = seat;
        this.showtime = showtime;
        this.isAvailable = isAvailable;
        this.id = new SeatAvailabilityId(seat.getId(), showtime.getId());
    }

    // Getters and Setters
    public SeatAvailabilityId getId() {
        return id;
    }

    public void setId(SeatAvailabilityId id) {
        this.id = id;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
        if (seat != null) {
            if (id == null) {
                id = new SeatAvailabilityId();
            }
            id.setSeatId(seat.getId());
        }
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
        if (showtime != null) {
            if (id == null) {
                id = new SeatAvailabilityId();
            }
            id.setShowtimeId(showtime.getId());
        }
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean available) {
        isAvailable = available;
    }
}
