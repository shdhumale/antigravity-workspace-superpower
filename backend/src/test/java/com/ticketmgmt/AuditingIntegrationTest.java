package com.ticketmgmt;

import com.ticketmgmt.entity.TicketEntity;
import com.ticketmgmt.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class AuditingIntegrationTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    void testCreatedAtAndUpdatedAtPopulated() {
        TicketEntity ticket = new TicketEntity();
        ticket.setName("Audit Test Ticket");
        ticket.setStatus("NEW");

        TicketEntity saved = ticketRepository.save(ticket);

        // These should be populated by JPA Auditing
        assertNotNull(saved.getCreatedAt(), "createdAt should not be null");
        assertNotNull(saved.getUpdatedAt(), "updatedAt should not be null");
    }
}
