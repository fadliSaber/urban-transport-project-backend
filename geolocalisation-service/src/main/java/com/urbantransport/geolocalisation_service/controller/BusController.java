package com.urbantransport.geolocalisation_service.controller;

import com.urbantransport.geolocalisation_service.model.Bus;
import com.urbantransport.geolocalisation_service.model.Status;
import com.urbantransport.geolocalisation_service.service.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bus")
public class BusController {
    private final BusService busService;

    @PostMapping
    public ResponseEntity<Bus> saveLocation(@RequestBody Bus busLocation) {
        try {
            return new ResponseEntity<>(busService.saveLocation(busLocation), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Bus>> getLocationsById(@PathVariable UUID id) {
        return ResponseEntity.ok(busService.getLocationById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<Bus>> getLocationsByBusId(@RequestParam String busId) {
        List<Bus> allBuses = busService.getAllBusesOrderedByTimestamp();
        return ResponseEntity.ok(allBuses.stream()
                .filter(bus -> bus.getBusId().equals(busId))
                .collect(Collectors.toList()));
    }

    @GetMapping("/current/{busId}")
    public ResponseEntity<Bus> getCurrentLocation(@PathVariable String busId) {
        return ResponseEntity.of(busService.getCurrentLocation(busId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Bus>> getBusesByStatus(@PathVariable Status status) {
        List<Bus> allBuses = busService.getAllBusesOrderedByTimestamp();
        List<Bus> buses = allBuses.stream().filter(bus -> bus.getStatus().equals(status)).collect(Collectors.toList());
        if (buses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(buses, HttpStatus.OK);
    }

    @GetMapping("/locations/after")
    public ResponseEntity<List<Bus>> getLocationsAfter(@RequestParam LocalDateTime timestamp) {
        List<Bus> allBuses = busService.getAllBusesOrderedByTimestamp();
        List<Bus> buses = allBuses.stream().filter(bus -> bus.getTimestamp().isAfter(timestamp)).collect(Collectors.toList());
        if (buses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(buses, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bus>> getAllBusesOrderedByTimestamp() {
        List<Bus> buses = busService.getAllBusesOrderedByTimestamp();
        if (buses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(buses, HttpStatus.OK);
    }
}
