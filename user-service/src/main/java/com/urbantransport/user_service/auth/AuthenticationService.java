package com.urbantransport.user_service.auth;

import com.urbantransport.user_service.config.JwtService;
import com.urbantransport.user_service.email.EmailService;
import com.urbantransport.user_service.exception.InvalidTokenException;
import com.urbantransport.user_service.exception.UserAlreadyExistsException;
import com.urbantransport.user_service.model.Admin;
import com.urbantransport.user_service.model.Passenger;
import com.urbantransport.user_service.model.SecureToken;
import com.urbantransport.user_service.repository.AdminRepository;
import com.urbantransport.user_service.repository.PassengerRepository;
import com.urbantransport.user_service.repository.SecureTokenRepository;
import com.urbantransport.user_service.service.PassengerService;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final AdminRepository adminRepository;
  private final PassengerRepository passengerRepository;
  private final SecureTokenRepository secureTokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final EmailService emailService;
  private final PassengerService passengerService;

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
    return AuthenticationResponse.builder().accessToken(jwtToken).build();
  }

  public AuthenticationResponse registerPassenger(RegisterUser request) {
    if (passengerService.checkIfPassengerExits(request.getEmail())) {
      throw new UserAlreadyExistsException("Passenger already exists");
    }
    var passenger = Passenger
      .builder()
      .email(request.getEmail())
      .password(passwordEncoder.encode(request.getPassword()))
      .lastName(request.getLastName())
      .firstName(request.getFirstName())
      .tokens(new ArrayList<SecureToken>())
      .isVerified(true)
      .build();

    passengerRepository.save(passenger);
    return AuthenticationResponse
      .builder()
      .accessToken(jwtService.generateToken(passenger))
      .build();
  }

  public ResponseEntity<String> signup(RegisterUser request)
    throws MessagingException, UnsupportedEncodingException {
    if (passengerService.checkIfPassengerExits(request.getEmail())) {
      throw new UserAlreadyExistsException("Passenger already exists");
    }
    var passenger = Passenger
      .builder()
      .email(request.getEmail())
      .password(passwordEncoder.encode(request.getPassword()))
      .lastName(request.getLastName())
      .firstName(request.getFirstName())
      .tokens(new ArrayList<SecureToken>())
      .build();

    var secureToken = SecureToken
      .builder()
      .token(jwtService.generateToken(passenger))
      .passenger(passenger)
      .expiryDate(LocalDateTime.now().plusHours(24))
      .build();

    passengerRepository.save(passenger);
    var passenger2 = passengerRepository.findByEmail(request.getEmail()).get();
    secureTokenRepository.save(secureToken);
    passenger2.getTokens().add(secureToken);
    passengerRepository.save(passenger2);
    sendRegistrationConfirmationEmail(request.getEmail());
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
      var refreshToken = jwtService.generateRefreshToken(admin.get());
      return AuthenticationResponse
        .builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build();
    } else {
      var passenger = passengerRepository.findByEmail(request.getEmail());
      if (passenger.isPresent()) {
        if (!passenger.get().isVerified()) {
          return AuthenticationResponse
            .builder()
            .accessToken("NOT_VERIFIED")
            .build();
        }
        var jwtToken = jwtService.generateToken(passenger.get());
        var refreshToken = jwtService.generateRefreshToken(passenger.get());
        return AuthenticationResponse
          .builder()
          .accessToken(jwtToken)
          .refreshToken(refreshToken)
          .build();
      } else {
        return AuthenticationResponse
          .builder()
          .accessToken("INVALID_TOKEN")
          .refreshToken("INVALID_TOKEN")
          .build();
      }
    }
  }

  public ResponseEntity<String> sendRegistrationConfirmationEmail(String email)
    throws UnsupportedEncodingException, MessagingException {
    if (!passengerService.checkIfPassengerExits(email)) {
      return ResponseEntity.ok("Passenger does not exist");
    }

    var passenger = passengerRepository.findByEmail(email).get();
    var token = passenger
      .getTokens()
      .stream()
      .reduce((first, second) -> second)
      .get()
      .getToken();

    String url = UriComponentsBuilder
      .fromHttpUrl("http://localhost:8080")
      .path("/auth/register/verify")
      .queryParam("token", token)
      .toUriString();

    emailService.sendEmail(
      email,
      "Email Verification",
      "Please click the link (within 24 hours) below to confirm your registration: " +
      url
    );
    return ResponseEntity.ok(
      "Registration confirmation email sent successfully"
    );
  }

  public ResponseEntity<String> verifyPassenger(String token) {
    var secureToken = secureTokenRepository
      .findByToken(token)
      .orElseThrow(() -> new InvalidTokenException());
    if (secureToken.getExpiryDate().isBefore(LocalDateTime.now())) {
      return ResponseEntity.ok("Token expired");
    }
    var passenger = secureToken.getPassenger();
    passenger.setVerified(true);
    passengerRepository.save(passenger);
    return ResponseEntity.ok("Passenger verified successfully");
  }

  public AuthenticationResponse refreshToken(String refreshToken) {
    var claims = jwtService.extractAllClaims(refreshToken);
    var passenger = passengerRepository
      .findByEmail(claims.getSubject())
      .orElseThrow(() -> new InvalidTokenException());
    if (!passenger.isVerified()) {
      return AuthenticationResponse
        .builder()
        .accessToken("NOT_VERIFIED")
        .build();
    }
    var jwtToken = jwtService.generateToken(passenger);
    return AuthenticationResponse
      .builder()
      .accessToken(jwtToken)
      .refreshToken(refreshToken)
      .build();
  }
}
