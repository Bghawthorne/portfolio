package com.hawthorne_labs.springboot.services;

import com.hawthorne_labs.springboot.entities.Charge;
import com.hawthorne_labs.springboot.entities.Employee;
import com.hawthorne_labs.springboot.entities.Payment;
import com.hawthorne_labs.springboot.entities.Schedule;
import com.hawthorne_labs.springboot.repositories.EmployeeRepository;
import com.hawthorne_labs.springboot.repositories.ScheduleRepository;
import com.hawthorne_labs.springboot.types.ChargeType;
import com.hawthorne_labs.springboot.types.PaymentType;
import com.hawthorne_labs.springboot.utils.date.HolidayCheck;
import com.hawthorne_labs.springboot.utils.date.WeekendCheck;
import com.hawthorne_labs.springboot.utils.finance.Payroll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    @Autowired
    private  EmployeeRepository  employeeRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    public Schedule saveSchedule(Schedule schedule) {
        // Ensure schedule start is set
        if (schedule.getStart() == null) {
            throw new IllegalArgumentException("Schedule start date/time must be set");
        }
        Employee employee = employeeRepository.findById(schedule.getEmployee().getId())
                .orElseThrow(() -> new NoSuchElementException("Employee not found with id: " + schedule.getEmployee().getId()));
        boolean isHoliday = HolidayCheck.isHoliday(schedule.getStart());
        boolean isWeekend = WeekendCheck.isWeekend(schedule.getStart());

        Payment payment = Payment.builder()
                .employee(employee)
                .isHoliday(isHoliday)
                .isWeekend(isWeekend)
                .amount(Payroll.calculatePaymentAmount(
                        employee,
                        schedule.getDuration(),
                        isHoliday,
                        isWeekend
                ))
                .type(PaymentType.SCHEDULED)
                .paymentDate(schedule.getStart().toLocalDate())
                .build();

        Charge charge = Charge.builder()
                .client(schedule.getClient())
                .isHoliday(HolidayCheck.isHoliday(schedule.getStart()))
                .isWeekend(WeekendCheck.isWeekend(schedule.getStart()))
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
