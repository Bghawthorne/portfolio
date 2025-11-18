package com.hawthorne_labs.springboot.dto;

import java.time.LocalDateTime;

public record ScheduleDTO(
        Long id,
        LocalDateTime start,
        LocalDateTime end,
        float duration,
        String googleUrl,
        PaymentDTO payment,
        ChargeDTO charge,
        EmployeeDTO employee,
        ClientDTO client
) {}
