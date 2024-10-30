package com.urbantransport.geolocalisation_service.repository;

import com.urbantransport.geolocalisation_service.model.Bus;
import com.urbantransport.geolocalisation_service.model.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusRepository extends CrudRepository<Bus, UUID> {
    List<Bus> findByBusId(String busId);
    List<Bus> findByStatus(Status status);
    List<Bus> findByTimestampAfter(Date timestamp);
    Optional<Bus> findTopByBusIdOrderByTimestampDesc(String busId);
    List<Bus> findAllByOrderByTimestampDesc();
}
