package com.hawthorne_labs.springboot.services;

import com.hawthorne_labs.springboot.entities.*;
import com.hawthorne_labs.springboot.repositories.ClientRepository;
import com.hawthorne_labs.springboot.repositories.EmployeeRepository;
import com.hawthorne_labs.springboot.repositories.ScheduleRepository;
import com.hawthorne_labs.springboot.types.ChargeType;
import com.hawthorne_labs.springboot.types.PaymentType;
import com.hawthorne_labs.springboot.utils.date.HolidayCheck;
import com.hawthorne_labs.springboot.utils.date.WeekendCheck;
import com.hawthorne_labs.springboot.utils.finance.Billing;
import com.hawthorne_labs.springboot.utils.finance.Payroll;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    public ScheduleService(
            ScheduleRepository scheduleRepository,
            EmployeeRepository employeeRepository,
            ClientRepository clientRepository
    ) {
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
    }

    public Schedule saveSchedule(Schedule schedule) {
        if (schedule.getStart() == null) {
            throw new IllegalArgumentException("Schedule start date/time must be set");
        }

        Employee employee = employeeRepository.findById(schedule.getEmployee().getId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Employee not found with id: " + schedule.getEmployee().getId()
                ));

        Client client = clientRepository.findById(schedule.getClient().getId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Client not found with id: " + schedule.getClient().getId()
                ));

        boolean isHoliday = HolidayCheck.isHoliday(schedule.getStart());
        boolean isWeekend = WeekendCheck.isWeekend(schedule.getStart());

        float duration = Duration.between(schedule.getStart(), schedule.getEnd()).toHours();

        Payment payment = Payment.builder()
                .employee(employee)
                .isHoliday(isHoliday)
                .isWeekend(isWeekend)
                .amount(Payroll.calculatePaymentAmount(
                        employee, duration, isHoliday, isWeekend
                ))
                .type(PaymentType.SCHEDULED)
                .paymentDate(schedule.getStart().toLocalDate())
                .build();

        Charge charge = Charge.builder()
                .client(client)
                .isHoliday(isHoliday)
                .isWeekend(isWeekend)
                .amount(Billing.calculateChargeAmount(
                        client, duration, isHoliday, isWeekend
                ))
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
