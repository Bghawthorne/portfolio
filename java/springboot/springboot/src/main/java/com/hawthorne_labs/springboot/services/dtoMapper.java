package com.hawthorne_labs.springboot.services;

import com.hawthorne_labs.springboot.dto.*;
import com.hawthorne_labs.springboot.entities.*;

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

    public static ClientDTO toClientDTO(Client client) {
        List<ScheduleDTO> schedules = client.getSchedules()== null ? List.of() :
                client.getSchedules().stream()
                        .map(dtoMapper::toScheduleDTO)
                        .collect(Collectors.toList());

        List<ChargeDTO> charges = client.getCharges() == null ? List.of() :
                client.getCharges().stream()
                        .map(dtoMapper::toChargeDTO)
                        .collect(Collectors.toList());

        return new ClientDTO(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getIsActive(),
                client.getPocName(),
                client.getPocEmail(),
                client.getWeekdayRate(),
                client.getWeekdayRate(),
                client.getHolidayWeekdayRate(),
                client.getHolidayWeekendRate(),
                schedules,
                charges
        );
    }

    public static ScheduleDTO toScheduleDTO(Schedule schedule) {
        PaymentDTO paymentDTO = schedule.getPayment() != null ? toPaymentDTO(schedule.getPayment()) : null;
        ChargeDTO chargeDTO = schedule.getCharge() != null ? toChargeDTO(schedule.getCharge()) : null;

        return new ScheduleDTO(
                schedule.getId(),
                schedule.getStart(),
                schedule.getEnd(),
                schedule.getDuration(),
                schedule.getGoogleUrl(),
                paymentDTO,
                chargeDTO
        );
    }

    public static PaymentDTO toPaymentDTO(Payment payment) {
        return new PaymentDTO(
                payment.getId(),
                payment.getAmount(),
                payment.getType(),
                payment.getDescription(),
                payment.getPaymentDate()
        );
    }

    public static ChargeDTO toChargeDTO(Charge charge) {
        return new ChargeDTO(
                charge.getId(),
                charge.getAmount(),
                charge.getType(),
                charge.getDescription(),
                charge.getChargeDate()
        );
    }
}
