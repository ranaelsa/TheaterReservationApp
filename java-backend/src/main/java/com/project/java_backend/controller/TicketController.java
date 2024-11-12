package com.project.java_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.java_backend.model.Ticket;
import com.project.java_backend.service.TicketService;

@RestController
@RequestMapping("api/tickets")
public class TicketController {

    @Autowired
	private TicketService ticketService;

	@DeleteMapping(value="/{id}", consumes="application/json", produces="application/json")
	public ResponseEntity<Void> cancelTicket(@PathVariable Long id) {
		ticketService.cancelTicket(id);
		return ResponseEntity.noContent().build();
	}
}
