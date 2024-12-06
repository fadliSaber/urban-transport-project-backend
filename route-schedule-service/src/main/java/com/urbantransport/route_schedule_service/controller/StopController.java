package com.urbantransport.route_schedule_service.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.urbantransport.route_schedule_service.entity.Stop;
import com.urbantransport.route_schedule_service.services.StopService;

@RestController
@RequestMapping("/stop")
public class StopController {

    @Autowired
    private StopService stopService;

    @GetMapping("/{id}")
    public ResponseEntity<Stop> getLocation(@PathVariable("id") UUID id) {
        Stop selectedStop = stopService.getStop(id);
        if(selectedStop != null) return new ResponseEntity<Stop>(selectedStop, HttpStatus.FOUND);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/location/{id}")
    public ResponseEntity<Stop> setLocation(@PathVariable("id") UUID id, @RequestParam String location) {
        stopService.setLocation(id, location);
        Stop selecteStop = stopService.getStop(id);
        if(selecteStop != null) return ResponseEntity.ok(selecteStop);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Stop> createStop(@RequestBody Stop stop) {
        Stop createdStop = stopService.createStop(stop);
        return ResponseEntity.ok(createdStop);
    }
}