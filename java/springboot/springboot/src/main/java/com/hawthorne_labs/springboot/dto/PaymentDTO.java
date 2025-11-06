package com.hawthorne_labs.springboot.dto;

import com.hawthorne_labs.springboot.types.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentDTO(
        Long id,
        BigDecimal amount,
        PaymentType type,
        String description,
        LocalDate paymentDate,
        Boolean isHoliday,
        Boolean isWeekend
) {}