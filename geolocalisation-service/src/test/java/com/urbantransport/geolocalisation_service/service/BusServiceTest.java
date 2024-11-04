package com.urbantransport.geolocalisation_service.service;

import com.urbantransport.geolocalisation_service.model.Bus;
import com.urbantransport.geolocalisation_service.model.Status;
import com.urbantransport.geolocalisation_service.repository.BusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BusServiceTest {

    @Mock
    private BusRepository busRepository;
    @InjectMocks
    private BusService busService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveLocation() {
        Bus bus = new Bus(UUID.randomUUID(), "test_bus1", 34.0, -118.0, new Date(), Status.ACTIF);
        when(busRepository.save(bus)).thenReturn(bus);

        Bus result = busService.saveLocation(bus);

        assertEquals(bus, result);
        verify(busRepository, times(1)).save(bus);
    }

    @Test
    void getLocationsByBusId() {
        String busId = "test_bus1";
        List<Bus> busList = List.of(new Bus(UUID.randomUUID(), busId, 34.0, -118.0, new Date(), Status.ACTIF));
        when(busRepository.findByBusId(busId)).thenReturn(busList);

        List<Bus> result = busService.getLocationsByBusId(busId);

        assertEquals(busList, result);
        verify(busRepository, times(1)).findByBusId(busId);
    }

    @Test
    void getCurrentLocation() {
        String busId = "test_bus1";
        Bus latestBus = new Bus(UUID.randomUUID(), busId, 34.0, -118.0, new Date(), Status.ACTIF);
        when(busRepository.findTopByBusIdOrderByTimestampDesc(busId)).thenReturn(Optional.of(latestBus));

        Optional<Bus> result = busService.getCurrentLocation(busId);

        assertEquals(Optional.of(latestBus), result);
        verify(busRepository, times(1)).findTopByBusIdOrderByTimestampDesc(busId);
    }

    @Test
    void getBusesByStatus() {
        Status status = Status.ACTIF;
        List<Bus> busList = List.of(new Bus(UUID.randomUUID(), "test_bus1", 34.0, -118.0, new Date(), status));
        when(busRepository.findByStatus(status)).thenReturn(busList);

        List<Bus> result = busService.getBusesByStatus(status);

        assertEquals(busList, result);
        verify(busRepository, times(1)).findByStatus(status);
    }

    @Test
    void getLocationsAfter() {
        Date timestamp = new Date();
        List<Bus> busList = List.of(new Bus(UUID.randomUUID(), "test_bus1", 34.0, -118.0, new Date(), Status.ACTIF));
        when(busRepository.findByTimestampAfter(timestamp)).thenReturn(busList);

        List<Bus> result = busService.getLocationsAfter(timestamp);

        assertEquals(busList, result);
        verify(busRepository, times(1)).findByTimestampAfter(timestamp);
    }

    @Test
    void getAllBusesOrderedByTimestamp() {
        List<Bus> busList = List.of(new Bus(UUID.randomUUID(), "test_bus1", 34.0, -118.0, new Date(), Status.ACTIF));
        when(busRepository.findAllByOrderByTimestampDesc()).thenReturn(busList);

        List<Bus> result = busService.getAllBusesOrderedByTimestamp();

        assertEquals(busList, result);
        verify(busRepository, times(1)).findAllByOrderByTimestampDesc();
    }
}