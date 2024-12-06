package com.urbantransport.route_schedule_service.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urbantransport.route_schedule_service.entity.Route;
import com.urbantransport.route_schedule_service.repository.RouteRepository;

@Service
public class RouteService {
    
    @Autowired
    private RouteRepository routeRepo;

    public Route getRouteById(UUID id) {
        return routeRepo.findById(id).orElse(null);
    }

    public Route createRoute(Route route) {
        return routeRepo.save(route);
    }

    public void addStop(UUID routeId, UUID stopId, int index) {
        List<UUID> stops;
        Route selectedRoute = routeRepo.findById(routeId).orElse(null);
        if(selectedRoute != null) {
            stops = selectedRoute.getStop_ids();
            stops.add(index, stopId);
            selectedRoute.setStop_ids(stops);
            routeRepo.save(selectedRoute);
        }
    }

    public void removeStop(UUID routeId, UUID stopId) {
        List<UUID> stops;
        Route selectedRoute = routeRepo.findById(routeId).orElse(null);
        if(selectedRoute != null) {
            stops = selectedRoute.getStop_ids();
            stops.remove(stopId);
            selectedRoute.setStop_ids(stops);
            routeRepo.save(selectedRoute);
        }
    }


    public UUID getNextStop(UUID routeId, UUID current_stop) {
        Route selectedRoute = routeRepo.findById(routeId).orElse(null);
        if(selectedRoute != null) {
            int current_stop_index = selectedRoute.getStop_ids().indexOf(current_stop);
            if(current_stop_index == selectedRoute.getStop_ids().size()-1) return selectedRoute.getStop_ids().get(current_stop_index-1);
            else return selectedRoute.getStop_ids().get(current_stop_index+1);
        } else {
            System.err.println("route doesnt exist");
            return null;
        }
    }
}
