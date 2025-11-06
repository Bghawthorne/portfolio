package com.hawthorne_labs.springboot.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentDTO(
        Long id,
        BigDecimal amount,
        LocalDateTime createdAt
) {}