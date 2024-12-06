package com.urbantransport.ticket_service.service.implementation;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.urbantransport.ticket_service.exception.NotFoundException;
import com.urbantransport.ticket_service.model.PaymentResponse;
import com.urbantransport.ticket_service.model.Ticket;
import com.urbantransport.ticket_service.repository.TicketRepository;
import com.urbantransport.ticket_service.service.TicketService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

  private final TicketRepository ticketRepository;

  @Override
  public Ticket purchaseTicket(Ticket ticket) throws StripeException {
    Ticket newTicket = new Ticket();
    newTicket.setPrice(ticket.getPrice());
    newTicket.setSeatNumber(ticket.getSeatNumber());
    newTicket.setPassengerEmail(ticket.getPassengerEmail());
    newTicket.setPaymentStatus("PENDING");
    newTicket.setPurchaseDate(LocalDateTime.now());

    Ticket savedTicket = ticketRepository.save(newTicket);

    String paymentIntentId = createPaymentIntent(ticket.getPrice())
      .getIntentId();

    savedTicket.setStripePaymentId(paymentIntentId);
    return ticketRepository.save(savedTicket);
  }

  @Override
  public Ticket getTicket(UUID id) {
    return ticketRepository
      .findById(id)
      .orElseThrow(() -> new NotFoundException("Ticket not found"));
  }

  @Override
  public List<Ticket> getAllTickets() {
    return ticketRepository.findAll();
  }

  @Override
  public Ticket updateTicket(UUID id, Ticket newticket) {
    Ticket ticket = ticketRepository
      .findById(id)
      .orElseThrow(() -> new NotFoundException("Ticket not found"));

    ticket.setPrice(newticket.getPrice());
    ticket.setSeatNumber(newticket.getSeatNumber());
    ticket.setPassengerEmail(newticket.getPassengerEmail());
    ticket.setPaymentStatus(newticket.getPaymentStatus());
    ticket.setPurchaseDate(newticket.getPurchaseDate());
    ticket.setStripePaymentId(newticket.getStripePaymentId());

    return ticketRepository.save(ticket);
  }

  @Override
  public void deleteTicket(UUID id) {
    ticketRepository.deleteById(id);
  }

  private PaymentResponse createPaymentIntent(Long price)
    throws StripeException {
    PaymentIntentCreateParams params = PaymentIntentCreateParams
      .builder()
      .setAmount(price * 100L)
      .putMetadata("ticket", "bus_ticket")
      .setCurrency("mad")
      .setAutomaticPaymentMethods(
        PaymentIntentCreateParams.AutomaticPaymentMethods
          .builder()
          .setEnabled(true)
          .build()
      )
      .build();

    PaymentIntent intent = PaymentIntent.create(params);

    return new PaymentResponse(intent.getId(), intent.getClientSecret());
  }

  @Override
  public void finalizePurchase(String paymentIntentId) {
    Ticket ticket = ticketRepository
      .findByStripePaymentId(paymentIntentId)
      .orElseThrow(() ->
        new NotFoundException(
          "Ticket not found for payment intent: " + paymentIntentId
        )
      );

    ticket.setPaymentStatus("SUCCESS");
    ticketRepository.save(ticket);
  }

  @Override
  public void cancelPurchase(String paymentIntentId) {
    Ticket ticket = ticketRepository
      .findByStripePaymentId(paymentIntentId)
      .orElseThrow(() ->
        new NotFoundException(
          "Ticket not found for payment intent: " + paymentIntentId
        )
      );

    ticket.setPaymentStatus("FAILED");
    ticketRepository.save(ticket);
  }
}
