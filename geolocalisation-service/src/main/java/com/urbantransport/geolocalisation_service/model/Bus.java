package com.urbantransport.geolocalisation_service.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Bus")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String busId;
    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;
    private Status status;
}
