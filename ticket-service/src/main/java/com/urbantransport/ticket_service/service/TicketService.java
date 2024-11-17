package com.urbantransport.ticket_service.service;

import com.stripe.exception.StripeException;
import com.urbantransport.ticket_service.model.Ticket;
import java.util.List;
import java.util.UUID;

public interface TicketService {
  public Ticket purchaseTicket(Ticket ticket) throws StripeException;

  public Ticket getTicket(UUID id);

  public List<Ticket> getAllTickets();

  public Ticket updateTicket(UUID id, Ticket newticket);

  public void deleteTicket(UUID id);

  public void finalizePurchase(String paymentIntentId);
}
