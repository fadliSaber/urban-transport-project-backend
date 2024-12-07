package com.urbantransport.notification_service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TicketPurchaseEvent {
    private String ticketId;
    private String userId;

}

