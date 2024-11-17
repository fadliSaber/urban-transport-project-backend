package com.urbantransport.ticket_service.controller;

import com.stripe.exception.StripeException;
import com.urbantransport.ticket_service.model.FinalizePurchaseRequest;
import com.urbantransport.ticket_service.model.Ticket;
import com.urbantransport.ticket_service.service.TicketService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

  private final TicketService ticketService;

  @PostMapping("/purchase")
  public ResponseEntity<Ticket> purchaseTicket(@RequestBody Ticket ticket)
    throws StripeException {
    return ResponseEntity.ok(ticketService.purchaseTicket(ticket));
  }

  @PostMapping("/finalize")
  public ResponseEntity<String> finalizePurchase(
    @RequestBody FinalizePurchaseRequest finalizePurchaseRequest
  ) {
    ticketService.finalizePurchase(
      finalizePurchaseRequest.getPaymentIntentId()
    );
    return ResponseEntity.ok("Purchase finalized");
  }

  @PostMapping("/cancel")
  public ResponseEntity<String> cancelPurchase(
    @RequestBody String paymentIntentId
  ) {
    ticketService.cancelPurchase(paymentIntentId);
    return ResponseEntity.ok("Purchase canceled");
  }

  @GetMapping("/getticket")
  public ResponseEntity<Ticket> getTicket(@RequestBody UUID id) {
    return ResponseEntity.ok(ticketService.getTicket(id));
  }

  @GetMapping("/alltickets")
  public ResponseEntity<List<Ticket>> getAllTickets() {
    return ResponseEntity.ok(ticketService.getAllTickets());
  }

  @PutMapping("/updateticket")
  public ResponseEntity<Ticket> updateTicket(
    @RequestBody UUID id,
    @RequestBody Ticket newticket
  ) {
    return ResponseEntity.ok(ticketService.updateTicket(id, newticket));
  }

  @DeleteMapping("/deleteticket")
  public ResponseEntity<String> deleteTicket(@RequestBody UUID id) {
    ticketService.deleteTicket(id);
    return ResponseEntity.ok("Ticket deleted");
  }
}
