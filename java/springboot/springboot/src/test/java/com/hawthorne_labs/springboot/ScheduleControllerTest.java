package com.hawthorne_labs.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawthorne_labs.springboot.entities.Schedule;
import com.hawthorne_labs.springboot.services.ScheduleService;
import com.hawthorne_labs.springboot.controllers.ScheduleController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ScheduleController.class)
public class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    @Autowired
    private ObjectMapper objectMapper;

    private Schedule schedule1;
    private Schedule schedule2;

    @BeforeEach
    void setUp() {
        schedule1 = Schedule.builder()
                .id(1L)
                .scheduleStart(LocalDateTime.of(2025, 11, 4, 9, 0))
                .scheduleEnd(LocalDateTime.of(2025, 11, 4, 10, 0))
                .build();

        schedule2 = Schedule.builder()
                .id(2L)
                .scheduleStart(LocalDateTime.of(2025, 11, 4, 11, 0))
                .scheduleEnd(LocalDateTime.of(2025, 11, 4, 12, 30))
                .build();
    }

    @Test
    void testGetAllSchedules() throws Exception {
        List<Schedule> schedules = Arrays.asList(schedule1, schedule2);
        Mockito.when(scheduleService.getAllSchedules()).thenReturn(schedules);

        mockMvc.perform(get("/api/schedules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(schedules.size()))
                .andExpect(jsonPath("$[0].id").value(schedule1.getId()))
                .andExpect(jsonPath("$[1].id").value(schedule2.getId()));
    }

    @Test
    void testGetScheduleById() throws Exception {
        Mockito.when(scheduleService.getScheduleById(1L)).thenReturn(Optional.of(schedule1));

        mockMvc.perform(get("/api/schedules/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(schedule1.getId()))
                .andExpect(jsonPath("$.duration").value(schedule1.getDuration()));
    }

    @Test
    void testCreateSchedule() throws Exception {
        Schedule newSchedule = Schedule.builder()
                .start(LocalDateTime.of(2025, 11, 4, 14, 0))
                .end(LocalDateTime.of(2025, 11, 4, 15, 0))
                .build();

        Schedule savedSchedule = Schedule.builder()
                .id(3L)
                .start(newSchedule.getStart())
                .end(newSchedule.getEnd())
                .build();

        Mockito.when(scheduleService.saveSchedule(any(Schedule.class))).thenReturn(savedSchedule);

        mockMvc.perform(post("/api/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSchedule)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.duration").value(savedSchedule.getDuration()));
    }

    @Test
    void testDeleteSchedule() throws Exception {
        doNothing().when(scheduleService).deleteSchedule(1L);

        mockMvc.perform(delete("/api/schedules/1"))
                .andExpect(status().isOk());
    }
}
