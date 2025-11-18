package com.hawthorne_labs.springboot.controllers;

import com.hawthorne_labs.springboot.entities.Schedule;
import com.hawthorne_labs.springboot.services.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // Get all schedules
    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    // Get schedule by ID
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id)
                .map(ResponseEntity::ok)                     // wrap the entity in 200 OK
                .orElse(ResponseEntity.notFound().build()); // 404 if not found
    }
    // Create a new schedule
    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        Schedule saved = scheduleService.saveSchedule(schedule);
        return ResponseEntity.ok(saved);
    }

    // Update an existing schedule
    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody Schedule schedule) {


        return scheduleService.getScheduleById(id)
                .map(existing -> {
                    existing.setStart(schedule.getStart());
                    existing.setEnd(schedule.getEnd());
                    Schedule updated = scheduleService.saveSchedule(existing);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a schedule
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        if (scheduleService.getScheduleById(id).isPresent()) {
            scheduleService.deleteSchedule(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
