package com.urbantransport.user_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "secure_token")
public class SecureToken {

  @Id
  private String id;

  private String token;

  private LocalDateTime expiryDate;

  @JsonBackReference
  private Passenger passenger;
}
