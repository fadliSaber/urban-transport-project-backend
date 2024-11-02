package com.urbantransport.user_service.auth;

import com.urbantransport.user_service.config.JwtAuthenticationFilter;
import com.urbantransport.user_service.config.JwtService;
import com.urbantransport.user_service.email.EmailService;
import com.urbantransport.user_service.model.Admin;
import com.urbantransport.user_service.model.Passenger;
import com.urbantransport.user_service.repository.AdminRepository;
import com.urbantransport.user_service.repository.PassengerRepository;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final AdminRepository adminRepository;
  private final PassengerRepository passengerRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationManager authenticationManager;
  private final EmailService emailService;

  public AuthenticationResponse registerAdmin(RegisterUser request) {
    var admin = Admin
      .builder()
      .email(request.getEmail())
      .password(passwordEncoder.encode(request.getPassword()))
      .lastName(request.getLastName())
      .firstName(request.getFirstName())
      .build();
    adminRepository.save(admin);
    var jwtToken = jwtService.generateToken(admin);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  public ResponseEntity<String> signup(RegisterUser request)
    throws MessagingException, UnsupportedEncodingException {
    var passenger = Passenger
      .builder()
      .email(request.getEmail())
      .password(passwordEncoder.encode(request.getPassword()))
      .lastName(request.getLastName())
      .firstName(request.getFirstName())
      .build();

    passengerRepository.save(passenger);
    emailService.sendEmail(
      request.getEmail(),
      "Request sent successfully",
      "Congratulations, your sign up request has been sent successfully."
    );
    return ResponseEntity.ok("Sign Up request sent successfully");
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.getEmail(),
        request.getPassword()
      )
    );

    var admin = adminRepository.findByEmail(request.getEmail());
    if (admin.isPresent()) {
      var jwtToken = jwtService.generateToken(admin.get());
      return AuthenticationResponse.builder().token(jwtToken).build();
    } else {
      var passenger = passengerRepository.findByEmail(request.getEmail());
      if (passenger.isPresent()) {
        var jwtToken = jwtService.generateToken(passenger.get());
        return AuthenticationResponse.builder().token(jwtToken).build();
      } else {
        return AuthenticationResponse.builder().token("INVALID_TOKEN").build();
      }
    }
  }

  public AuthenticationResponse changePassword(AuthenticationRequest request) {
    var admin = adminRepository.findByEmail(
      jwtAuthFilter.getCurrentUserEmail()
    );
    if (admin.isPresent()) {
      if (
        passwordEncoder.matches(
          request.getOldPassword(),
          admin.get().getPassword()
        )
      ) {
        admin
          .get()
          .setPassword(passwordEncoder.encode(request.getNewPassword()));
        adminRepository.save(admin.get());
        var jwtToken = jwtService.generateToken(admin.get());
        return AuthenticationResponse.builder().token(jwtToken).build();
      } else {
        return AuthenticationResponse.builder().token("INVALID_TOKEN").build();
      }
    } else {
      var passenger = passengerRepository.findByEmail(
        jwtAuthFilter.getCurrentUserEmail()
      );
      if (passenger.isPresent()) {
        if (
          passwordEncoder.matches(
            request.getOldPassword(),
            passenger.get().getPassword()
          )
        ) {
          passenger
            .get()
            .setPassword(passwordEncoder.encode(request.getNewPassword()));
          passengerRepository.save(passenger.get());
          var jwtToken = jwtService.generateToken(passenger.get());
          return AuthenticationResponse.builder().token(jwtToken).build();
        } else {
          return AuthenticationResponse
            .builder()
            .token("INVALID_TOKEN")
            .build();
        }
      } else {
        return AuthenticationResponse.builder().token("INVALID_TOKEN").build();
      }
    }
  }
}
