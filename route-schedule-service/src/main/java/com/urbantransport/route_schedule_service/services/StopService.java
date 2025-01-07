package com.urbantransport.route_schedule_service.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urbantransport.route_schedule_service.entity.Stop;
import com.urbantransport.route_schedule_service.repository.StopRepository;

@Service
public class StopService {
    
    @Autowired
    private StopRepository stopRepo;


    public List<Stop> getAll() {
        return stopRepo.findAll();
    }
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


    public void setLocation(UUID stopId, int lat, int lng) {
        Stop selectedStop = stopRepo.findById(stopId).orElse(null);
        if(selectedStop != null) {
            selectedStop.setLat(lat);
            selectedStop.setLng(lng);
            stopRepo.save(selectedStop);
        }
    }

    public List<Stop> findStopsByIds(List<UUID> stopIds) {
        return stopRepo.findAllById(stopIds);
    }
}
