package com.urbantransport.geolocalisation_service.controller;

import com.urbantransport.geolocalisation_service.model.Bus;
import com.urbantransport.geolocalisation_service.model.Status;
import com.urbantransport.geolocalisation_service.service.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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

    @GetMapping("/{busId}")
    @Cacheable(value = "busLocations", key = "#busId")
    public ResponseEntity<List<Bus>> getLocationsByBusId(@PathVariable String busId) {
        return ResponseEntity.ok(busService.getLocationsByBusId(busId));
    }

    @GetMapping("/current/{busId}")
    @Cacheable(value = "currentBusLocation", key = "#busId",unless = "#result == null")
    public ResponseEntity<Bus> getCurrentLocation(@PathVariable String busId) {
        return ResponseEntity.of(busService.getCurrentLocation(busId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Bus>> getBusesByStatus(@PathVariable Status status) {
        List<Bus> buses = busService.getBusesByStatus(status);
        if (buses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(buses, HttpStatus.OK);
    }

    @GetMapping("/locations/after")
    public ResponseEntity<List<Bus>> getLocationsAfter(@RequestParam Date timestamp) {
        List<Bus> buses = busService.getLocationsAfter(timestamp);
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
