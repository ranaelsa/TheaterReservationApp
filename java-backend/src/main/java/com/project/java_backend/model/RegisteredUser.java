package com.project.java_backend.model;

import jakarta.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
public class RegisteredUser {
    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100)
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    @NotBlank(message = "Password is required")
    private String password;

    private String address;

    // Payment Information
    @Size(min = 16, max = 16, message = "Card number must be 16 digits")
    private long cardNumber;

    @Size(min = 6, max = 6, message = "Date must be in format DDMMYY")
    private int expiryDate;

    @Size(min = 3, max = 3, message = "CVC must be 3 digits")
    private int cvc;

    // Relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ticket> tickets;

    //Default constructor
    public RegisteredUser(){

    }

    public RegisteredUser(String name, String email, String password, String address, int cardNumber, int expiryDate, int cvc) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvc = cvc;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(int expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }
}
