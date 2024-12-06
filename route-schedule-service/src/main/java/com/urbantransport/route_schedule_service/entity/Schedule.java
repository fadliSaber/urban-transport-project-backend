package com.urbantransport.route_schedule_service.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;


import lombok.Data;

@Data
@Table("schedule")
public class Schedule {

    @Id
    private UUID id = UUID.randomUUID();
    private UUID route_id;
    private LocalDateTime departure;
    private LocalDateTime arrival;
}
