package com.urbantransport.route_schedule_service.entity;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

@Data
@Table("route")
public class Route {

    @Id
    private UUID id = UUID.randomUUID();
    private String routeNumber;
    private String routeName;
    public List<UUID> stopIds;
    private int frequency;
    private int duration;
}
