package com.hawthorne_labs.springboot.utils.finance;

import com.hawthorne_labs.springboot.entities.Client;

import java.math.BigDecimal;

public class Billing {
    public static BigDecimal calculateChargeAmount(Client client, float hours, Boolean isHoliday, Boolean isWeekend) {
        if (isHoliday && isWeekend) {

            return client.getHolidayWeekendRate()
                    .multiply(BigDecimal.valueOf(hours));

        } else if (isHoliday) {
            // Example: holiday only
            return client.getHolidayWeekdayRate()
                    .multiply(BigDecimal.valueOf(hours));

        } else if (isWeekend) {
            // Example: weekend only
            return client.getWeekendRate()
                    .multiply(BigDecimal.valueOf(hours));
        } else {
            // Neither holiday nor weekend
            return client.getWeekdayRate()
                    .multiply(BigDecimal.valueOf(hours));
        }
    }

}
