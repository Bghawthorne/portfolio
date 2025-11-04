package com.hawthorne_labs.springboot;

import com.hawthorne_labs.springboot.entities.Schedule;
import com.hawthorne_labs.springboot.repositories.ScheduleRepository;
import com.hawthorne_labs.springboot.services.ScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(ScheduleService.class) // Import service for testing
class ScheduleServiceTest {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    void testSaveAndRetrieveSchedule() {
        Schedule schedule = Schedule.builder()
                .start(LocalDateTime.of(2025, 11, 4, 9, 0))
                .end(LocalDateTime.of(2025, 11, 4, 10, 30))
                .build();

        // Save schedule
        Schedule saved = scheduleService.saveSchedule(schedule);

        assertNotNull(saved.getId());
        assertEquals(90f, saved.getDuration()); // Duration in minutes

        // Retrieve by ID
        Schedule retrieved = scheduleService.getScheduleById(saved.getId()).orElseThrow();
        assertEquals(saved.getId(), retrieved.getId());
        assertEquals(90f, retrieved.getDuration());

        // Retrieve all
        List<Schedule> schedules = scheduleService.getAllSchedules();
        assertEquals(1, schedules.size());
    }

    @Test
    void testDeleteSchedule() {
        Schedule schedule = Schedule.builder()
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusHours(1))
                .build();

        Schedule saved = scheduleService.saveSchedule(schedule);
        Long id = saved.getId();

        scheduleService.deleteSchedule(id);

        assertFalse(scheduleService.getScheduleById(id).isPresent());
    }
}
