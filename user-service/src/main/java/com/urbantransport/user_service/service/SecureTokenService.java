package com.urbantransport.user_service.service;

import com.urbantransport.user_service.model.SecureToken;
import java.util.List;

public interface SecureTokenService {
  List<SecureToken> getAll();

  SecureToken createSecureToken(SecureToken securetoken);

  SecureToken updateSecureToken(String id, SecureToken newsecuretoken);

  void deleteSecureToken(String id);

  SecureToken getSecureToken(String id);

  SecureToken getSecureTokenByToken(String token);
}
