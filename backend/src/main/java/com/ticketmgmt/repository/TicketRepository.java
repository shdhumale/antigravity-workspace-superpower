package com.ticketmgmt.repository;

import com.ticketmgmt.entity.TicketEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    Page<TicketEntity> findByNameContainingOrDescriptionContaining(String name, String description, Pageable pageable);
}
