package com.hawthorne_labs.springboot.dto;

import java.math.BigDecimal;
import java.util.List;

public record ClientDTO(
        Long id,
        String firstName,
        String lastName,
        Boolean isActive,
        String pocName,
        String pocEmail,
        BigDecimal weekdayRate,
        BigDecimal weekendRate,
        BigDecimal holidayWeekdayRate,
        BigDecimal holidayWeekendRate,
        List<ScheduleDTO> schedules,
        List<ChargeDTO> charges
) {}