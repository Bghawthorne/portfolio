package com.hawthorne_labs.springboot.entities;

import com.hawthorne_labs.springboot.entities.Client;
import com.hawthorne_labs.springboot.entities.Employee;
import com.hawthorne_labs.springboot.entities.Charge;
import com.hawthorne_labs.springboot.entities.Payment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedules")
@Data
@NoArgsConstructor  // Lombok generates a no-args constructor
@AllArgsConstructor // Lombok generates an all-args constructor
@Builder            // Optional: allows using builder pattern
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "schedule_start")
    private LocalDateTime start;
    @Column(name = "schedule_end")
    private LocalDateTime end;

    private float duration;

    // automatically compute duration before saving or updating
    @PrePersist
    @PreUpdate
    private void calculateDuration() {
        if (start != null && end != null) {
            this.duration = Duration.between(start, end).toHours();
        } else {
            this.duration = 0f;
        }
    }


    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @OneToOne(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;

    @OneToOne(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private Charge charge;



    private String googleUrl;


}
