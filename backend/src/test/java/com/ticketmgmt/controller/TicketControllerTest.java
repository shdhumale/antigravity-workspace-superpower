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
}
