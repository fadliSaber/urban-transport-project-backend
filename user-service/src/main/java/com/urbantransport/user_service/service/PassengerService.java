package com.urbantransport.user_service.service;

import com.urbantransport.user_service.auth.AuthenticationRequest;
import com.urbantransport.user_service.auth.AuthenticationResponse;
import com.urbantransport.user_service.model.Passenger;
import java.util.List;

public interface PassengerService {
  List<Passenger> getAll();

  Passenger updatePassenger(String id, Passenger newpassanger);

  void deletePassenger(String id);

  Passenger getPassenger(String id);

  Passenger getPassengerByEmail(String email);

  AuthenticationResponse changePassword(AuthenticationRequest request);

  boolean checkIfPassengerExits(String email);
}
