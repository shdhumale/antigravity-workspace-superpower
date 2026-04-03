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

    @PutMapping("/{id}")
    public ResponseEntity<TicketEntity> updateTicket(@PathVariable Long id, @RequestBody TicketEntity ticketDetails) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ticketRepository.findById(id).map(ticket -> {
            ticket.setName(ticketDetails.getName());
            ticket.setDescription(ticketDetails.getDescription());
            ticket.setStatus(ticketDetails.getStatus());
            TicketEntity updated = ticketRepository.save(ticket);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
