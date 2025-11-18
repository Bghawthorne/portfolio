package com.hawthorne_labs.springboot.dto;

import com.hawthorne_labs.springboot.utils.build.Resource;

import java.time.LocalDateTime;

public record ScheduleDTO(
        Long id,
        LocalDateTime start,
        LocalDateTime end,
        float duration,
        String googleUrl,
        PaymentDTO payment,
        ChargeDTO charge,
        Resource employeeResource,
        Resource clientResource
) {}
