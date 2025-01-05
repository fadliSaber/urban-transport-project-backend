package com.urbantransport.route_schedule_service.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urbantransport.route_schedule_service.entity.Stop;
import com.urbantransport.route_schedule_service.repository.StopRepository;

@Service
public class StopService {
    
    @Autowired
    private StopRepository stopRepo;

    public Stop getStop(UUID stopId) {
        Stop selectedStop = stopRepo.findById(stopId).orElse(null);
        if(selectedStop != null) {
            return selectedStop;
        } else {
            return null;
        }
    }

    public Stop createStop(Stop stop) {
        return stopRepo.save(stop);
    }


    public void setLocation(UUID stopId, String location) {
        Stop selectedStop = stopRepo.findById(stopId).orElse(null);
        if(selectedStop != null) {
            selectedStop.setLocation(location);
            stopRepo.save(selectedStop);
        }
    }
}
