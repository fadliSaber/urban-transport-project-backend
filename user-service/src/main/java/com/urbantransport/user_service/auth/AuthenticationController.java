package com.urbantransport.user_service.auth;

import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
// @CrossOrigin(origins = "${spring.graphql.cors.allowed-origins}")
@RequestMapping("/auth")
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register/admin")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<AuthenticationResponse> registerAdmin(
    @RequestBody RegisterUser request
  ) {
    return ResponseEntity.ok(service.registerAdmin(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
    @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/changePassword")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT', 'ROLE_CLUB')")
  public ResponseEntity<AuthenticationResponse> changePassword(
    @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.changePassword(request));
  }

  // @PostMapping("/forgotPassword")
  // public ResponseEntity<AuthenticationResponse> forgotPassword(
  //   @RequestBody AuthenticationRequest request
  // ) throws MessagingException, UnsupportedEncodingException {
  //   return ResponseEntity.ok(service.forgotPassword(request));
  // }

  @PostMapping("/signup")
  public ResponseEntity<String> signUp(@RequestBody RegisterUser request)
    throws MessagingException, UnsupportedEncodingException {
    return service.signup(request);
  }
}
