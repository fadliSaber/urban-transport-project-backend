package com.urbantransport.user_service.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {

  private String lastName;

  private String firstName;

  private String password;

  private String email;
}
