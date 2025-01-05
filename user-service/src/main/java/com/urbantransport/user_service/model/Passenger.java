package com.urbantransport.user_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "passenger")
public class Passenger implements UserDetails {

  @Id
  private String id;

  private String firstName;

  private String lastName;

  private String email;

  private String password;

  @Builder.Default
  private Role role = Role.PASSENGER;

  private Instant createdAt;

  private boolean isVerified;

  @JsonManagedReference
  private List<SecureToken> tokens;

  protected void onCreate() {
    createdAt = Instant.now();
    isVerified = false;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
