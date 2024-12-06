package com.urbantransport.user_service.repository;

import com.urbantransport.user_service.model.Admin;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, String> {
  Optional<Admin> findByEmail(String email);
}
