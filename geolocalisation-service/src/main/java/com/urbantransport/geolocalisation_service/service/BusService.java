package com.urbantransport.geolocalisation_service.service;

import com.urbantransport.geolocalisation_service.model.Bus;
import com.urbantransport.geolocalisation_service.model.Status;
import com.urbantransport.geolocalisation_service.repository.BusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BusService {
    private final BusRepository busRepository;

    // Save or update bus location
    public Bus saveLocation(Bus busLocation) {
        return busRepository.save(busLocation);
    }

    // Retrieve all locations of a specific bus by busId
    public List<Bus> getLocationsByBusId(String busId) {
        return busRepository.findByBusId(busId);
    }

    // Retrieve the latest location of a specific bus by busId
    public Optional<Bus> getCurrentLocation(String busId) {
        return busRepository.findTopByBusIdOrderByTimestampDesc(busId);
    }

    // Retrieve buses by their status
    public List<Bus> getBusesByStatus(Status status) {
        return busRepository.findByStatus(status);
    }

    // Retrieve all bus locations with a timestamp after the specified date
    public List<Bus> getLocationsAfter(Date timestamp) {
        return busRepository.findByTimestampAfter(timestamp);
    }

    // Retrieve all buses ordered by their timestamp in descending order
    public List<Bus> getAllBusesOrderedByTimestamp() {
        return busRepository.findAllByOrderByTimestampDesc();
    }

    public void clearCache() {
        busRepository.deleteAll();
    }
}
