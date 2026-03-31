package com.ticketmgmt.controller;

import com.ticketmgmt.entity.TicketEntity;
import com.ticketmgmt.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
@CrossOrigin(origins = "*") // For angular dev
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public List<TicketEntity> getAllTickets() {
        return ticketRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<TicketEntity> createTicket(@RequestBody TicketEntity ticket) {
        if (ticket.getStatus() == null) {
            ticket.setStatus("NEW");
        }
        TicketEntity saved = ticketRepository.save(ticket);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public List<TicketEntity> searchTickets(@RequestParam("query") String query) {
        return ticketRepository.findByNameContainingOrDescriptionContaining(query, query);
    }
}
