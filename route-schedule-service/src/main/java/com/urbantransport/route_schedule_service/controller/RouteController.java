package com.urbantransport.route_schedule_service.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.urbantransport.route_schedule_service.entity.Route;
import com.urbantransport.route_schedule_service.entity.Stop;
import com.urbantransport.route_schedule_service.services.RouteService;
import com.urbantransport.route_schedule_service.services.StopService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private StopService stopService;

    @GetMapping("/{id}")
    public ResponseEntity<Route> getRoute(@PathVariable("id") UUID id) {
        Route selectedRoute = routeService.getRouteById(id);
        if(selectedRoute != null) return ResponseEntity.ok(selectedRoute);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Route> createRoute(@RequestBody Route route) {
        Route createdRoute = routeService.createRoute(route);
        return ResponseEntity.ok(createdRoute);
    }

    @PostMapping("/addStop/{id}")
    public ResponseEntity<Route> addStop(@PathVariable("id") UUID id, @RequestParam UUID stopId, @RequestParam int index) {
        routeService.addStop(id, stopId, index);
        Route selectedRoute = routeService.getRouteById(id);
        return new ResponseEntity<Route>(selectedRoute, HttpStatus.CREATED);
    }

    @PostMapping("/removeStop/{id}")
    public ResponseEntity<Route> removeStop(@PathVariable("id") UUID id, @RequestParam UUID stopId) {
        routeService.removeStop(id, stopId);
        Route selectedRoute = routeService.getRouteById(id);
        return new ResponseEntity<Route>(selectedRoute, HttpStatus.CREATED);
    }

    @GetMapping("/nextStop/{id}")
    public ResponseEntity<Stop> getNextStop(@PathVariable("id") UUID id, @RequestParam UUID currentStopId) {
        UUID nextStopUUID = routeService.getNextStop(id, currentStopId);
        Stop selectedStop = stopService.getStop(nextStopUUID);
        return ResponseEntity.ok(selectedStop);
    }
    
    
}