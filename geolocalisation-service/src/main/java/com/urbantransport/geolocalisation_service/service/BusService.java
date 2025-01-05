package com.urbantransport.geolocalisation_service.service;

import com.urbantransport.geolocalisation_service.model.Bus;
import com.urbantransport.geolocalisation_service.model.Status;
import com.urbantransport.geolocalisation_service.repository.BusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Service
public class BusService {
    private final BusRepository busRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    // Save or update bus location
    public Bus saveLocation(Bus busLocation) {
        return busRepository.save(busLocation);
    }

    public Optional<Bus> getLocationById(UUID id) {
        return busRepository.findById(id);
    }

    // Retrieve all locations of a specific bus by busId
    public List<Bus> getLocationsByBusId(String busId) {
        return busRepository.findByBusId(busId);
    }

    // Retrieve the latest location of a specific bus by busId
    public Optional<Bus> getCurrentLocation(String busId) {
        // Get all keys matching the Bus prefix
        Set<String> keys = redisTemplate.keys("Bus:*");
        if (keys == null || keys.isEmpty()) {
            return Optional.empty();
        }
        // Iterate through the keys and find the matching busId
        for (String key : keys) {
            Map<Object, Object> busData = redisTemplate.opsForHash().entries(key);
            // Check if busId matches
            if (busId.equals(busData.get("busId"))) {
                // Parse the Bus object from the data
                Bus bus = new Bus(
                        UUID.fromString(key.split(":")[1]),
                        (String) busData.get("busId"),
                        Double.parseDouble((String) busData.get("latitude")),
                        Double.parseDouble((String) busData.get("longitude")),
                        LocalDateTime.parse((String) busData.get("timestamp"), DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                        Status.valueOf((String) busData.get("status"))
                );
                return Optional.of(bus);
            }
        }
        return Optional.empty();
    }

    // Retrieve buses by their status
    public List<Bus> getBusesByStatus(Status status) {
        return busRepository.findByStatus(status);
    }

    // Retrieve all bus locations with a timestamp after the specified date
    public List<Bus> getLocationsAfter(LocalDateTime timestamp) {
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
