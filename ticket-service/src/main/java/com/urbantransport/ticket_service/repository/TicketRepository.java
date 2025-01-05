package com.urbantransport.ticket_service.repository;

import com.urbantransport.ticket_service.model.Ticket;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
  Optional<List<Ticket>> findByPaymentStatus(String paymentStatus);
  Optional<Ticket> findByStripePaymentId(String stripePaymentId);
}
