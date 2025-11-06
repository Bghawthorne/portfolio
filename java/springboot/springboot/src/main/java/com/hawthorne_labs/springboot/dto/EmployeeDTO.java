package com.hawthorne_labs.springboot.dto;

import java.math.BigDecimal;
import java.util.List;

public record EmployeeDTO(
        Long id,
        String firstName,
        String lastName,
        Boolean isActive,
        String email,
        BigDecimal baseRate,
        List<ScheduleDTO> schedules,
        List<PaymentDTO> payments
) {}