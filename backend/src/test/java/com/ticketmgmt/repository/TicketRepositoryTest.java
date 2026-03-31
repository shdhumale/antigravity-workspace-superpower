package com.ticketmgmt.repository;

import com.ticketmgmt.entity.TicketEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    public void testSaveAndFindTicket() {
        TicketEntity ticket = new TicketEntity();
        ticket.setName("Database Issue");
        ticket.setDescription("Cannot connect to MySQL database.");
        ticket.setStatus("NEW");

        TicketEntity savedTicket = ticketRepository.save(ticket);

        assertThat(savedTicket.getId()).isNotNull();
        assertThat(ticketRepository.findById(savedTicket.getId()).isPresent()).isTrue();
    }
}
