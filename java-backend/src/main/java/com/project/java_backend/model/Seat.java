package com.project.java_backend.model;

import jakarta.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "seats", uniqueConstraints = @UniqueConstraint(columnNames = {"theater_id", "seat_number"}))
public class Seat {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Seat number is required")
    @Size(max = 5, message = "Seat number cannot exceed 5 characters")
    private String seatNumber; // e.g., "A1", "B2"

    private Double price;

    // Relationships
    @ManyToOne(optional = false)
    @JoinColumn(name = "theater_id", nullable = false)
    @JsonBackReference
    private Theater theater;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SeatAvailability> seatAvailabilities;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ticket> tickets;

    // Constructors
    public Seat() {
        // Default constructor
    }

    public Seat(String seatNumber, Theater theater) {
        this.seatNumber = seatNumber;
        this.theater = theater;
        this.price = 15.99;
    }

    // ID
    public Long getId() {
        return id;
    }

    // Price
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    // Seat Number
    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    // Theater
    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
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
