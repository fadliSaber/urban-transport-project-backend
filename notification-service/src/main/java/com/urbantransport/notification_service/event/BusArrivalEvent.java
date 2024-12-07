package com.urbantransport.notification_service.event;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
@Setter
public class BusArrivalEvent {
    private String id;
    private String stationName;
    private String userId;
    private LocalDateTime arrivalTime;


}


