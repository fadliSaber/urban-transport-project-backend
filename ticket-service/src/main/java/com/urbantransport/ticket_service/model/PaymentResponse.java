package com.urbantransport.ticket_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentResponse {

  private String intentId;
  private String clientSecret;
}
