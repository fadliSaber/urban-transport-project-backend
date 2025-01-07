package com.urbantransport.route_schedule_service.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urbantransport.route_schedule_service.entity.Route;
import com.urbantransport.route_schedule_service.entity.Stop;
import com.urbantransport.route_schedule_service.repository.RouteRepository;
import com.urbantransport.route_schedule_service.repository.StopRepository;

@Service
public class RouteService {
    
    @Autowired
    private RouteRepository routeRepo;

    @Autowired
    private StopRepository stopRepo;

    public Route getRouteById(UUID id) {
        return routeRepo.findById(id).orElse(null);
    }

    public List<Route> getAll() {
        return routeRepo.findAll();
    }

    public Route createRoute(Route route) {
        return routeRepo.save(route);
    }

    public void addStop(UUID routeId, UUID stopId, int index) {
        List<UUID> stops;
        Route selectedRoute = routeRepo.findById(routeId).orElse(null);
        if(selectedRoute != null) {
            stops = selectedRoute.getStopIds();
            stops.add(index, stopId);
            selectedRoute.setStopIds(stops);
            routeRepo.save(selectedRoute);
        }
    }

    public void removeStop(UUID routeId, UUID stopId) {
        List<UUID> stops;
        Route selectedRoute = routeRepo.findById(routeId).orElse(null);
        if(selectedRoute != null) {
            stops = selectedRoute.getStopIds();
            stops.remove(stopId);
            selectedRoute.setStopIds(stops);
            routeRepo.save(selectedRoute);
        }
    }


    public UUID getNextStop(UUID routeId, UUID current_stop) {
        Route selectedRoute = routeRepo.findById(routeId).orElse(null);
        if(selectedRoute != null) {
            int current_stop_index = selectedRoute.getStopIds().indexOf(current_stop);
            if(current_stop_index == selectedRoute.getStopIds().size()-1) return selectedRoute.getStopIds().get(current_stop_index-1);
            else return selectedRoute.getStopIds().get(current_stop_index+1);
        } else {
            System.err.println("route doesnt exist");
            return null;
        }
    }

    public Boolean containsStops(Route route, String fromStop, String toStop) {
        int c = 0;
        for (UUID stopId : route.getStopIds()) {
            Stop stop = stopRepo.findById(stopId).orElse(null);
            if (stop != null) {
                if (stop.getName().equalsIgnoreCase(fromStop)) {
                    c++;
                }else if(stop.getName().equalsIgnoreCase(toStop)) {
                    c++;
                }
            }
        }

        return c==2? true: false;
    }
}
