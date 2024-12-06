package com.urbantransport.user_service.service.implementation;

import com.urbantransport.user_service.auth.AuthenticationRequest;
import com.urbantransport.user_service.auth.AuthenticationResponse;
import com.urbantransport.user_service.config.JwtAuthenticationFilter;
import com.urbantransport.user_service.config.JwtService;
import com.urbantransport.user_service.exception.NotFoundException;
import com.urbantransport.user_service.model.Passenger;
import com.urbantransport.user_service.repository.PassengerRepository;
import com.urbantransport.user_service.service.PassengerService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PassengerServiceImpl implements PassengerService {

  private final PassengerRepository passengerRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtAuthenticationFilter jwtAuthFilter;
  private final JwtService jwtService;

  @Override
  public List<Passenger> getAll() {
    return passengerRepository.findAll();
  }

  @Override
  public Passenger updatePassenger(String id, Passenger newpassenger) {
    var currentUserEmail = jwtAuthFilter.getCurrentUserEmail();
    var currentUser = passengerRepository.findByEmail(currentUserEmail);

    if (!currentUser.isPresent()) {
      Passenger oldpassenger = passengerRepository
        .findById(id)
        .orElseThrow(() ->
          new NotFoundException("Passenger with id " + id + " not found")
        );

      oldpassenger.setEmail(newpassenger.getEmail());
      oldpassenger.setFirstName(newpassenger.getFirstName());
      oldpassenger.setLastName(newpassenger.getLastName());
      oldpassenger.setPassword(
        passwordEncoder.encode(newpassenger.getPassword())
      );
      return passengerRepository.save(oldpassenger);
    }

    Passenger oldpassenger = passengerRepository
      .findById(id)
      .orElseThrow(() ->
        new NotFoundException("Passenger with id " + id + " not found")
      );

    oldpassenger.setFirstName(newpassenger.getFirstName());
    oldpassenger.setLastName(newpassenger.getLastName());
    return passengerRepository.save(oldpassenger);
  }

  @Override
  public void deletePassenger(String id) {
    passengerRepository.deleteById(id);
  }

  @Override
  public Passenger getPassenger(String id) {
    return passengerRepository
      .findById(id)
      .orElseThrow(() ->
        new NotFoundException("Passenger with id " + id + " not found")
      );
  }

  @Override
  public Passenger getPassengerByEmail(String email) {
    return passengerRepository
      .findByEmail(email)
      .orElseThrow(() ->
        new NotFoundException("Passenger with email " + email + " not found")
      );
  }

  @Override
  public AuthenticationResponse changePassword(AuthenticationRequest request) {
    var passenger = passengerRepository.findByEmail(
      jwtAuthFilter.getCurrentUserEmail()
    );
    if (
      passenger.isPresent() &&
      passwordEncoder.matches(
        request.getOldPassword(),
        passenger.get().getPassword()
      ) &&
      !passwordEncoder.matches(
        request.getNewPassword(),
        passenger.get().getPassword()
      )
    ) {
      passenger
        .get()
        .setPassword(passwordEncoder.encode(request.getNewPassword()));
      passengerRepository.save(passenger.get());
      var jwtToken = jwtService.generateToken(passenger.get());
      return AuthenticationResponse.builder().accessToken(jwtToken).build();
    } else {
      return AuthenticationResponse
        .builder()
        .accessToken("INVALID_TOKEN")
        .build();
    }
  }

  @Override
  public boolean checkIfPassengerExits(String email) {
    return passengerRepository.findByEmail(email).isPresent();
  }
}
