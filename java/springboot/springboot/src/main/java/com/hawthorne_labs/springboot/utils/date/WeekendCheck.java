package com.hawthorne_labs.springboot.utils.date;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class WeekendCheck {

    /**
     * Returns true if the given LocalDateTime is a Saturday or Sunday.
     */
    public static boolean isWeekend(LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    // Test
    public static void main(String[] args) {
        LocalDateTime saturday = LocalDateTime.of(2025, 11, 8, 10, 0);
        LocalDateTime monday = LocalDateTime.of(2025, 11, 10, 10, 0);

        System.out.println(isWeekend(saturday)); // true
        System.out.println(isWeekend(monday));   // false
    }
}
