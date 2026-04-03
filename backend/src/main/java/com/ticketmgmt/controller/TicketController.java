package com.ticketmgmt.controller;

import com.ticketmgmt.entity.TicketEntity;
import com.ticketmgmt.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tickets")
@CrossOrigin(origins = "*") // For angular dev
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    @Cacheable(value = "tickets", key = "#pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<TicketEntity> getAllTickets(@PageableDefault(size = 10) Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

    @PostMapping
    @CacheEvict(value = "tickets", allEntries = true)
    public ResponseEntity<TicketEntity> createTicket(@RequestBody TicketEntity ticket) {
        if (ticket.getStatus() == null) {
            ticket.setStatus("NEW");
        }
        TicketEntity saved = ticketRepository.save(ticket);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    @Cacheable(value = "tickets", key = "'search_' + #query + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<TicketEntity> searchTickets(@RequestParam("query") String query,
            @PageableDefault(size = 10) Pageable pageable) {
        return ticketRepository.findByNameContainingOrDescriptionContaining(query, query, pageable);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "tickets", allEntries = true)
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
