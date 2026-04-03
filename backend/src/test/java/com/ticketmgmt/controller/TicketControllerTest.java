package com.ticketmgmt.controller;

import com.ticketmgmt.entity.TicketEntity;
import com.ticketmgmt.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketRepository ticketRepository;

    @Test
    public void testGetAllTickets() throws Exception {
        Mockito.when(ticketRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/tickets"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTicket() throws Exception {
        TicketEntity existingTicket = new TicketEntity();
        existingTicket.setId(1L);
        existingTicket.setName("Old Name");
        existingTicket.setDescription("Old Description");
        existingTicket.setStatus("NEW");

        Mockito.when(ticketRepository.findById(1L)).thenReturn(java.util.Optional.of(existingTicket));

        org.mockito.ArgumentCaptor<TicketEntity> captor = org.mockito.ArgumentCaptor.forClass(TicketEntity.class);
        Mockito.when(ticketRepository.save(captor.capture())).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/v1/tickets/1")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(
                        "{\"name\": \"Updated Name\", \"description\": \"Updated Description\", \"status\": \"DONE\"}"))
                .andExpect(status().isOk());

        TicketEntity saved = captor.getValue();
        org.junit.jupiter.api.Assertions.assertEquals("Updated Name", saved.getName(), "Name was not updated");
        org.junit.jupiter.api.Assertions.assertEquals("Updated Description", saved.getDescription(),
                "Description was not updated");
        org.junit.jupiter.api.Assertions.assertEquals("DONE", saved.getStatus(), "Status was not updated");
    }
}
