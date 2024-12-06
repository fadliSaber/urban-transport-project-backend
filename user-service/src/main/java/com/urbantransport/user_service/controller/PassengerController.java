package com.urbantransport.user_service.controller;

import com.urbantransport.user_service.model.Passenger;
import com.urbantransport.user_service.service.PassengerService;
import java.nio.file.AccessDeniedException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @CrossOrigin(origins = "${spring.graphql.cors.allowed-origins}")
@RequestMapping("/passenger")
@AllArgsConstructor
public class PassengerController {

  private final PassengerService passengerService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/passengers")
  List<Passenger> getAllPassengers() throws AccessDeniedException {
    return passengerService.getAll();
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PASSENGER')")
  @PutMapping("/uppassenger/{id}")
  Passenger updatePassenger(
    @PathVariable String id,
    @RequestBody Passenger newpassenger
  ) throws AccessDeniedException {
    return passengerService.updatePassenger(id, newpassenger);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PASSENGER')")
  @DeleteMapping("/delpassenger/{id}")
  void deletePassenger(@PathVariable String id) throws AccessDeniedException {
    passengerService.deletePassenger(id);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PASSENGER')")
  @GetMapping("/getpassenger/{id}")
  Passenger getPassenger(@PathVariable String id) throws AccessDeniedException {
    return passengerService.getPassenger(id);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/{email}")
  Passenger getPassengerByEmail(@PathVariable String email) {
    return passengerService.getPassengerByEmail(email);
  }
}
