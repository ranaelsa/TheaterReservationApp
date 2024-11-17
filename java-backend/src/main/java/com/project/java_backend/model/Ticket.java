package com.project.java_backend.model;

import jakarta.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tickets", uniqueConstraints = @UniqueConstraint(columnNames = {"showtime_id", "seat_id"}))
public class Ticket {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private Double price;

    @NotNull(message = "Purchase time is required")
    private LocalDateTime purchaseTime;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email; // Email of the purchaser

    @NotBlank(message = "Code is required.")
    @Column(unique = true)
    private String code; // Code to show the user

    // Relationships
    // Optional relationship to RegisteredUser
    @ManyToOne(optional = true) // Allow nullable user for non-registered users
    @JoinColumn(name = "user_id", nullable = true)
    @JsonBackReference("user-tickets")
    private RegisteredUser user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "showtime_id", nullable = false)
    @JsonBackReference("showtime-tickets")
    private Showtime showtime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seat_id", nullable = false)
    @JsonBackReference("ticket-seat")
    private Seat seat;

    // Constructors
    public Ticket() {
        // Default constructor
    }

    public Ticket(Double price, String email, RegisteredUser user, Showtime showtime, Seat seat) {
        this.price = price;
        this.email = email;
        this.user = user;
        this.showtime = showtime;
        this.seat = seat;
        this.purchaseTime = LocalDateTime.now();
        this.code = generateCode();
    }

    public Ticket(Double price, String email, Showtime showtime, Seat seat) {
        this.price = price;
        this.email = email;
        this.showtime = showtime;
        this.seat = seat;
        this.purchaseTime = LocalDateTime.now();
        this.code = generateCode();
    }
    

    private String generateCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase(); // Unique 8-character code
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

    // Purchase Time
    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // User
    public RegisteredUser getUser() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }

    // Showtime
    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    // Seat
    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    // Code
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
