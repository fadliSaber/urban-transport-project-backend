package com.urbantransport.notification_service.entity;

import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private String id = UUID.randomUUID().toString();
    private String title;
    private String description;
    private LocalDateTime createdAt ;



    }

