package com.urbantransport.user_service.repository;

import com.urbantransport.user_service.model.Passenger;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PassengerRepository extends MongoRepository<Passenger, String> {
  Optional<Passenger> findByEmail(String email);
}
