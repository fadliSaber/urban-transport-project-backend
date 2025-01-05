package com.urbantransport.user_service.service.implementation;

import com.urbantransport.user_service.exception.NotFoundException;
import com.urbantransport.user_service.model.SecureToken;
import com.urbantransport.user_service.repository.SecureTokenRepository;
import com.urbantransport.user_service.service.SecureTokenService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecureTokenServiceImpl implements SecureTokenService {

  private final SecureTokenRepository secureTokenRepository;

  @Override
  public List<SecureToken> getAll() {
    return secureTokenRepository.findAll();
  }

  @Override
  public SecureToken createSecureToken(SecureToken secureToken) {
    return secureTokenRepository.save(secureToken);
  }

  @Override
  public SecureToken updateSecureToken(String id, SecureToken newsecureToken) {
    SecureToken oldsecureToken = secureTokenRepository
      .findById(id)
      .orElseThrow(() ->
        new NotFoundException("SecureToken with id " + id + " not found")
      );

    oldsecureToken.setToken(newsecureToken.getToken());
    oldsecureToken.setExpiryDate(newsecureToken.getExpiryDate());
    oldsecureToken.setPassenger(newsecureToken.getPassenger());
    return secureTokenRepository.save(oldsecureToken);
  }

  @Override
  public void deleteSecureToken(String id) {
    secureTokenRepository.deleteById(id);
  }

  @Override
  public SecureToken getSecureToken(String id) {
    return secureTokenRepository
      .findById(id)
      .orElseThrow(() ->
        new NotFoundException("SecureToken with id " + id + " not found")
      );
  }

  @Override
  public SecureToken getSecureTokenByToken(String token) {
    return secureTokenRepository
      .findByToken(token)
      .orElseThrow(() ->
        new NotFoundException("SecureToken with token " + token + " not found")
      );
  }
}
