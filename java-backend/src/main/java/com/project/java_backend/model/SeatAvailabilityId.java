package com.project.java_backend.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SeatAvailabilityId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long seatId;
    private Long showtimeId;

    // Constructors
    public SeatAvailabilityId() {
        // Default constructor
    }

    public SeatAvailabilityId(Long seatId, Long showtimeId) {
        this.seatId = seatId;
        this.showtimeId = showtimeId;
    }

    // Getters and Setters
    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Long getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Long showtimeId) {
        this.showtimeId = showtimeId;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeatAvailabilityId that = (SeatAvailabilityId) o;

        if (!Objects.equals(seatId, that.seatId)) return false;
        return Objects.equals(showtimeId, that.showtimeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatId, showtimeId);
    }
}
