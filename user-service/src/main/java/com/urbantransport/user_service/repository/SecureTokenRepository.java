package com.urbantransport.user_service.repository;

import com.urbantransport.user_service.model.SecureToken;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SecureTokenRepository
  extends MongoRepository<SecureToken, String> {
  Optional<SecureToken> findByToken(String token);
}
