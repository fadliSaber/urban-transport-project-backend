package com.urbantransport.geolocalisation_service.repository;

import com.urbantransport.geolocalisation_service.model.Bus;
import com.urbantransport.geolocalisation_service.model.Status;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NonNullApi
@Repository
public interface BusRepository extends CrudRepository<Bus, UUID> {
    List<Bus> findByBusId(String busId);
    List<Bus> findByStatus(Status status);
    List<Bus> findByTimestampAfter(LocalDateTime timestamp);
    List<Bus> findAllByOrderByTimestampDesc();
}
