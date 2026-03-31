package com.ticketmgmt.repository;

import com.ticketmgmt.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    List<TicketEntity> findByNameContainingOrDescriptionContaining(String name, String description);
}
