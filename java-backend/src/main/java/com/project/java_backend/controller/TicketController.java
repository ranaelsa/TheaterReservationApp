package com.project.java_backend.controller;

import com.project.java_backend.model.Ticket;
import com.project.java_backend.model.RegisteredUser;
import com.project.java_backend.model.Seat;
import com.project.java_backend.model.Showtime;
import com.project.java_backend.service.RegisteredUserService;
import com.project.java_backend.service.SeatService;
import com.project.java_backend.service.ShowtimeService;
import com.project.java_backend.service.TicketService;
import com.project.java_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
	private TicketService ticketService;
	private ShowtimeService showtimeService;
	private RegisteredUserService registeredUserService;
	private SeatService seatService;

    // Create a new ticket
    @PostMapping(consumes = "application/json", produces = "application/json")
    public Ticket createTicket(@RequestParam Double price,
                               @RequestParam String email,
                               @RequestParam(required = false) Long userId,
                               @RequestParam Long showtimeId,
                               @RequestParam Long seatId) {
        // Assume user, showtime, and seat are fetched from services or repositories
        RegisteredUser user = (userId != null) ? registeredUserService.getUserById(userId) : null;// Fetch user by userId : null;
        Showtime showtime = showtimeService.getShowtimeById(showtimeId); // Fetch showtime by showtimeId;
        Seat seat = seatService.getSeatById(seatId); // Fetch seat by seatId;

        return ticketService.createTicket(price, email, user, showtime, seat);
    }

    // Cancel a ticket by ID
    @DeleteMapping(value = "/cancel/{id}", consumes = "application/json", produces = "application/json")
    public String cancelTicket(@PathVariable Long id) {
        ticketService.cancelTicket(id);
        return "Ticket canceled successfully.";
    }

    // Retrieve a list of tickets for a specific user
    @GetMapping(value = "/user/{userId}", consumes = "application/json", produces = "application/json")
    public List<Ticket> getTicketsByUserId(@PathVariable Long userId) {
        return ticketService.getTicketsByUserId(userId);
    }

    // Retrieve a single ticket by ID
    @GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public Ticket getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + id));
    }

    // Retrieve all tickets (for admin purposes)
    @GetMapping(consumes = "application/json", produces = "application/json")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }
}
