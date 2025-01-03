package com.urbantransport.notification_service.entity;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Notification")

public class Notification {
    private String id = UUID.randomUUID().toString();
    private String title;
    private String description;
    private LocalDateTime createdAt ;



    }

