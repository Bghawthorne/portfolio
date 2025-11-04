package com.hawthorne_labs.springboot.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor  // Lombok generates a no-args constructor
@AllArgsConstructor // Lombok generates an all-args constructor
@Builder            // Optional: allows using builder pattern

public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Boolean isActive;

    @Column()
    private String pocName;

    @Column(unique = true)
    private String pocEmail;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal weekdayRate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal weekendRate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal holidayWeekdayRate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal holidayWeekendRate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime modifiedAt;
}
