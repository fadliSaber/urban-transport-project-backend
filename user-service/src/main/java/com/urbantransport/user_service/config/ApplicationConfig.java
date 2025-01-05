package com.urbantransport.user_service.config;

import com.urbantransport.user_service.model.Admin;
import com.urbantransport.user_service.model.Passenger;
import com.urbantransport.user_service.repository.AdminRepository;
import com.urbantransport.user_service.repository.PassengerRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  public final AdminRepository adminRepository;
  public final PassengerRepository passengerRepository;

  @Bean
  public UserDetailsService userDetailsService()
    throws UsernameNotFoundException {
    return username -> {
      Optional<Admin> admin = adminRepository.findByEmail(username);
      if (admin.isPresent()) {
        return admin.get();
      } else {
        Optional<Passenger> passenger = passengerRepository.findByEmail(
          username
        );
        if (passenger.isPresent()) {
          return passenger.get();
        } else {
          throw new UsernameNotFoundException("User not found");
        }
      }
    };
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration config
  ) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
