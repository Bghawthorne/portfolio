package com.hawthorne_labs.springboot.services;

import com.hawthorne_labs.springboot.entities.Charge;
import com.hawthorne_labs.springboot.entities.Payment;
import com.hawthorne_labs.springboot.entities.Schedule;
import com.hawthorne_labs.springboot.repositories.ScheduleRepository;
import com.hawthorne_labs.springboot.types.ChargeType;
import com.hawthorne_labs.springboot.types.PaymentType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule saveSchedule(Schedule schedule) {
        // Ensure schedule start is set
        if (schedule.getStart() == null) {
            throw new IllegalArgumentException("Schedule start date/time must be set");
        }

        // Create Payment using Builder
        Payment payment = Payment.builder()
                .employee(schedule.getEmployee())
                .amount(new BigDecimal("100.00")) // ✅ use string to avoid floating precision errors
                .type(PaymentType.SCHEDULED)
                .paymentDate(schedule.getStart().toLocalDate()) // ✅ works because start is LocalDateTime
                .build();
        Charge charge = Charge.builder()
                        .client(schedule.getClient())
                        .amount(new BigDecimal(200.00))
                        .type(ChargeType.SCHEDULED)
                        .chargeDate(schedule.getStart().toLocalDate())
                        .build();

        schedule.setPayment(payment);
        schedule.setCharge(charge);
        charge.setSchedule(schedule);
        payment.setSchedule(schedule);
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Optional<Schedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

}
