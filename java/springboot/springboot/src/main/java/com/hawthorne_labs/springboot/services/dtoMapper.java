package com.hawthorne_labs.springboot.services;

import com.hawthorne_labs.springboot.dto.EmployeeDTO;
import com.hawthorne_labs.springboot.dto.ScheduleDTO;
import com.hawthorne_labs.springboot.dto.PaymentDTO;
import com.hawthorne_labs.springboot.entities.Employee;
import com.hawthorne_labs.springboot.entities.Schedule;
import com.hawthorne_labs.springboot.entities.Payment;

import java.util.List;
import java.util.stream.Collectors;

public class dtoMapper {

    public static EmployeeDTO toEmployeeDTO(Employee employee) {
        List<ScheduleDTO> schedules = employee.getSchedules() == null ? List.of() :
                employee.getSchedules().stream()
                        .map(dtoMapper::toScheduleDTO)
                        .collect(Collectors.toList());

        List<PaymentDTO> payments = employee.getPayments() == null ? List.of() :
                employee.getPayments().stream()
                        .map(dtoMapper::toPaymentDTO)
                        .collect(Collectors.toList());

        return new EmployeeDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getIsActive(),
                employee.getEmail(),
                employee.getBaseRate(),
                schedules,
                payments
        );
    }

    public static ScheduleDTO toScheduleDTO(Schedule schedule) {
        PaymentDTO paymentDTO = schedule.getPayment() != null ? toPaymentDTO(schedule.getPayment()) : null;

        return new ScheduleDTO(
                schedule.getId(),
                schedule.getStart(),
                schedule.getEnd(),
                schedule.getDuration(),
                schedule.getGoogleUrl(),
                paymentDTO
        );
    }

    public static PaymentDTO toPaymentDTO(Payment payment) {
        return new PaymentDTO(
                payment.getId(),
                payment.getAmount(),
                payment.getCreated()
        );
    }
}
