package com.hawthorne_labs.springboot.utils.finance;

import com.hawthorne_labs.springboot.entities.Employee;

import java.math.BigDecimal;

public class Payroll {

    public static BigDecimal calculatePaymentAmount(Employee employee, float hours, Boolean isHoliday, Boolean isWeekend) {
        BigDecimal baseRate = employee.getBaseRate();
        System.out.println("baseRate: " + baseRate);
        System.out.println("hours: " + hours);
        if (isHoliday && isWeekend) {

            return baseRate
                    .add(BigDecimal.ONE)
                    .multiply(BigDecimal.valueOf(1.5))
                    .multiply(BigDecimal.valueOf(hours));

        } else if (isHoliday) {
            // Example: holiday only
            return baseRate
                    .multiply(BigDecimal.valueOf(1.5))
                    .multiply(BigDecimal.valueOf(hours));
        } else if (isWeekend) {
            // Example: weekend only
            return baseRate
                    .add(BigDecimal.ONE)
                    .multiply(BigDecimal.valueOf(hours));
        } else {
            // Neither holiday nor weekend
            return baseRate
                    .multiply(BigDecimal.valueOf(hours));
        }
    }
}
