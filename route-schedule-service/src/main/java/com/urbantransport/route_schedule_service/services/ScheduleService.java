package com.urbantransport.route_schedule_service.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urbantransport.route_schedule_service.entity.Schedule;
import com.urbantransport.route_schedule_service.repository.ScheduleRepository;

@Service
public class ScheduleService {
    
    @Autowired
    private ScheduleRepository scheduleRepo;

    public Schedule getSchedule(UUID scheduleId) {
        Schedule selectedSchedule = scheduleRepo.findById(scheduleId).orElse(null);
        if(selectedSchedule != null) return selectedSchedule;
        else return null;
    }

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepo.save(schedule);
    }
    
    public void updateTimes(UUID scheduleId, LocalDateTime departure, LocalDateTime arrival) {
        Schedule selectedSchedule = scheduleRepo.findById(scheduleId).orElse(null);
        if(selectedSchedule != null) {
            selectedSchedule.setDeparture(departure);
            selectedSchedule.setArrival(arrival);
            scheduleRepo.save(selectedSchedule);
        }
    }

    public void updateRoute(UUID scheduleId, UUID routeId) {
        Schedule selectedSchedule = scheduleRepo.findById(scheduleId).orElse(null);
        if(selectedSchedule != null) {
            selectedSchedule.setRoute_id(routeId);
            scheduleRepo.save(selectedSchedule);
        }
    }
}
