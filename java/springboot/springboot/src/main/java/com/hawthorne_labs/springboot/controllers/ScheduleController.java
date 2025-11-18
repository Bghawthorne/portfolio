package com.hawthorne_labs.springboot.controllers;

import com.hawthorne_labs.springboot.dto.ScheduleDTO;
import com.hawthorne_labs.springboot.entities.Schedule;
import com.hawthorne_labs.springboot.services.ScheduleService;
import com.hawthorne_labs.springboot.services.DtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final DtoMapper dtoMapper;
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
        this.dtoMapper = dtoMapper;
    }

    // Get all schedules
    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    // Get schedule by ID
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDTO> getScheduleById(@PathVariable Long id) {
        Schedule schedule;
        schedule = scheduleService.getScheduleById(id)
                .orElseThrow(() -> new; (HttpStatus.NOT_FOUND, "Schedule not found"));
        ScheduleDTO scheduleDTO = dtoMapper.toScheduleDTO(schedule);
        return ResponseEntity.ok(scheduleDTO);

    // Create a new schedule
    @PostMapping
    public ResponseEntity<ScheduleDTO> createSchedule(@RequestBody Schedule schedule) {
        ScheduleDTO saved = scheduleService.saveSchedule(schedule);
        return ResponseEntity.ok(saved);
    }

    // Update an existing schedule
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDTO> updateSchedule(@PathVariable Long id, @RequestBody Schedule schedule) {


        return scheduleService.getScheduleById(id)
                .map(existing -> {
                    existing.setStart(schedule.getStart());
                    existing.setEnd(schedule.getEnd());
                    ScheduleDTO updated = scheduleService.saveSchedule(existing);
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
