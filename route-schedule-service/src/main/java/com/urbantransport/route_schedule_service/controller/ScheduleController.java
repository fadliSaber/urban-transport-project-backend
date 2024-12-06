package com.urbantransport.route_schedule_service.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.urbantransport.route_schedule_service.entity.Schedule;
import com.urbantransport.route_schedule_service.services.ScheduleService;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;


    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getSchedule(@PathVariable("id") UUID id) {
        Schedule selectedSchedule = scheduleService.getSchedule(id);
        if(selectedSchedule != null) return new ResponseEntity<Schedule>(selectedSchedule, HttpStatus.FOUND);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        Schedule createdSchedule = scheduleService.createSchedule(schedule);
        return ResponseEntity.ok(createdSchedule);
    }
    

    @PostMapping("/updatetime/{id}")
    public ResponseEntity<Schedule> updateTimes(@PathVariable("id") UUID id, @RequestParam LocalDateTime departure, @RequestParam LocalDateTime arrival) {
        scheduleService.updateTimes(id, departure, arrival);
        Schedule selectedSchedule = scheduleService.getSchedule(id);
        return ResponseEntity.ok(selectedSchedule);
    }

    @PostMapping("/updateRoute/{id}")
    public ResponseEntity<Schedule> updateRoute(@PathVariable("id") UUID id, @RequestParam UUID routeId) {
        scheduleService.updateRoute(id, routeId);
        Schedule selectSchedule = scheduleService.getSchedule(id);
        if(selectSchedule != null) return ResponseEntity.ok(selectSchedule);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}