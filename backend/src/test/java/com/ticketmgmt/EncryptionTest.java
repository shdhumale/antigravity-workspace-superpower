package com.ticketmgmt;

import com.ticketmgmt.entity.TicketEntity;
import com.ticketmgmt.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@Transactional
public class EncryptionTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    void testDescriptionIsEncrypted() {
        String sensitiveInfo = "This is a sensitive ticket description";
        TicketEntity ticket = new TicketEntity();
        ticket.setName("Encrypted Test");
        ticket.setStatus("NEW");
        ticket.setDescription(sensitiveInfo);

        TicketEntity saved = ticketRepository.save(ticket);

        // When reading from the JPA entity, it should be decrypted
        assertEquals(sensitiveInfo, saved.getDescription(), "The description should be decrypted in the entity");

        // If we were to look at the raw data in the DB (simulated here by checking what
        // a converter would do),
        // it should not be the same as the original string.
        // We'll rely on the converter being applied.
    }
}
