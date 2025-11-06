package com.hawthorne_labs.springboot.dto;

import com.hawthorne_labs.springboot.types.ChargeType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ChargeDTO(
        Long id,
        BigDecimal amount,
        ChargeType type,
        String description,
        LocalDate chargeDate,
        Boolean isHoliday,
        Boolean inWeekend
) {}
