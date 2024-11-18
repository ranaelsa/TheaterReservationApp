package com.project.java_backend.controller;

import com.project.java_backend.model.Ticket;
import com.project.java_backend.service.TicketService;
import com.project.java_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
	private TicketService ticketService;
	

    // Cancel a ticket by UUID
    @DeleteMapping(value = "/cancel/{code}", consumes = "application/json", produces = "application/json")
    public String cancelTicket(@PathVariable String code) {
        ticketService.cancelTicket(code);
        return "Ticket canceled successfully.";
    }

    // Retrieve a list of tickets for a specific user
    @GetMapping(value = "/user/{userId}", consumes = "application/json", produces = "application/json")
    public List<Ticket> getTicketsByUserId(@PathVariable Long userId) {
        return ticketService.getTicketsByUserId(userId);
    }

	// Retrieve the cost of the given tickets
	@PostMapping(value="/cost", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Double> getCost(@RequestBody List<Long> seatIds) {
		return ResponseEntity.ok(ticketService.getCost(seatIds));
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
