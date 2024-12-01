package com.urbantransport.geolocalisation_service.service;

import com.urbantransport.geolocalisation_service.model.Bus;
import com.urbantransport.geolocalisation_service.model.Status;
import com.urbantransport.geolocalisation_service.repository.BusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BusServiceTest {

    @Mock
    private BusRepository busRepository;
    @Mock
    private RedisTemplate<String, Object> redisTemplate;
    @Mock
    private HashOperations<String, Object, Object> hashOperations;
    @InjectMocks
    private BusService busService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
    }

    @Test
    void saveLocation() {
        Bus bus = new Bus(UUID.randomUUID(), "test_bus1", 34.0, -118.0, LocalDateTime.now(), Status.ACTIF);
        when(busRepository.save(bus)).thenReturn(bus);

        Bus result = busService.saveLocation(bus);

        assertEquals(bus, result);
        verify(busRepository, times(1)).save(bus);
    }

    @Test
    void getLocationById() {
        UUID id = UUID.randomUUID(); // Unique ID for the test
        String busId = "test_bus1";  // Example Bus ID
        Bus bus = new Bus(id, busId, 34.0, -118.0, LocalDateTime.now(), Status.INACTIF);
        // Mock repository behavior
        when(busRepository.findById(id)).thenReturn(Optional.of(bus));
        // Act
        Optional<Bus> result = busService.getLocationById(id);
        // Assert
        assertTrue(result.isPresent()); // Ensure the result is present
        assertEquals(bus, result.get()); // Check that the result matches the expected bus
        verify(busRepository, times(1)).findById(id); // Verify the repository method was called once
    }


    @Test
    void getLocationsByBusId() {
        UUID id = UUID.randomUUID(); // Unique ID for the test
        String busId = "test_bus1";  // Example Bus ID
        List<Bus> busList = List.of(new Bus(id, busId, 34.0, -118.0, LocalDateTime.now(), Status.NON_OPERATIONEL));
        when(busRepository.findByBusId(busId)).thenReturn(busList);

        List<Bus> result = busService.getLocationsByBusId(busId);

        assertEquals(busList, result);
        verify(busRepository, times(1)).findByBusId(busId);
    }

    @Test
    void getCurrentLocation() {
        String busId = "test_bus1";
        UUID id = UUID.randomUUID();
        String redisKey = "Bus:" + id;
        Map<Object, Object> busData = Map.of(
                "busId", busId,
                "latitude", "34.0",
                "longitude", "-118.0",
                "timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                "status", "ACTIF"
        );
        // Mock RedisTemplate behavior
        when(redisTemplate.keys("Bus:*")).thenReturn(Set.of(redisKey));
        when(redisTemplate.opsForHash().entries(redisKey)).thenReturn(busData);

        Optional<Bus> result = busService.getCurrentLocation(busId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(busId, result.get().getBusId());
        assertEquals(34.0, result.get().getLatitude());
        assertEquals(-118.0, result.get().getLongitude());
        assertEquals(Status.ACTIF, result.get().getStatus());
        verify(redisTemplate, times(1)).keys("Bus:*");
        verify(redisTemplate.opsForHash(), times(1)).entries(redisKey);
    }

    @Test
    void getBusesByStatus() {
        Status status = Status.ACTIF;
        List<Bus> busList = List.of(new Bus(UUID.randomUUID(), "test_bus1", 34.0, -118.0, LocalDateTime.now(), status));
        when(busRepository.findByStatus(status)).thenReturn(busList);

        List<Bus> result = busService.getBusesByStatus(status);

        assertEquals(busList, result);
        verify(busRepository, times(1)).findByStatus(status);
    }

    @Test
    void getLocationsAfter() {
        LocalDateTime timestamp = LocalDateTime.now();
        List<Bus> busList = List.of(new Bus(UUID.randomUUID(), "test_bus1", 34.0, -118.0, LocalDateTime.now(), Status.ACTIF));
        when(busRepository.findByTimestampAfter(timestamp)).thenReturn(busList);

        List<Bus> result = busService.getLocationsAfter(timestamp);

        assertEquals(busList, result);
        verify(busRepository, times(1)).findByTimestampAfter(timestamp);
    }

    @Test
    void getAllBusesOrderedByTimestamp() {
        List<Bus> busList = List.of(new Bus(UUID.randomUUID(), "test_bus1", 34.0, -118.0, LocalDateTime.now(), Status.ACTIF));
        when(busRepository.findAllByOrderByTimestampDesc()).thenReturn(busList);

        List<Bus> result = busService.getAllBusesOrderedByTimestamp();

        assertEquals(busList, result);
        verify(busRepository, times(1)).findAllByOrderByTimestampDesc();
    }
}