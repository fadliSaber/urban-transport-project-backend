package com.urbantransport.route_schedule_service.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

@Data
@Table("stop")
public class Stop {

    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    private String location;
}
