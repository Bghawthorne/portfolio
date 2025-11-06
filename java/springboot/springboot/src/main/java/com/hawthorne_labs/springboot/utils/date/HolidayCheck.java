package com.hawthorne_labs.springboot.utils.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

public class HolidayCheck {

    public static boolean isHoliday(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        Month month = date.getMonth();
        int day = date.getDayOfMonth();
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        int year = date.getYear();

        // Fixed-date holidays
        if ((month == Month.JANUARY && day == 1) ||             // New Year's Day
                (month == Month.JULY && day == 4) ||                // Independence Day
                (month == Month.NOVEMBER && day == 11) ||           // Veterans Day
                (month == Month.DECEMBER && day == 24) ||           // Christmas Eve
                (month == Month.DECEMBER && day == 25) ||           // Christmas Day
                (month == Month.DECEMBER && day == 31)) {           // New Year's Eve
            return true;
        }

        // Movable holidays
        if (date.equals(easterSunday(year))) return true;           // Easter
        if (date.equals(memorialDay(year))) return true;           // Memorial Day
        if (date.equals(laborDay(year))) return true;              // Labor Day
        if (date.equals(thanksgiving(year))) return true;          // Thanksgiving

        return false;
    }

    // Easter Sunday (using Anonymous Gregorian algorithm)
    private static LocalDate easterSunday(int year) {
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int month = (h + l - 7 * m + 114) / 31;
        int day = ((h + l - 7 * m + 114) % 31) + 1;
        return LocalDate.of(year, month, day);
    }

    // Memorial Day: last Monday of May
    private static LocalDate memorialDay(int year) {
        return LocalDate.of(year, Month.MAY, 31)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    // Labor Day: first Monday of September
    private static LocalDate laborDay(int year) {
        return LocalDate.of(year, Month.SEPTEMBER, 1)
                .with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
    }

    // Thanksgiving: fourth Thursday of November
    private static LocalDate thanksgiving(int year) {
        return LocalDate.of(year, Month.NOVEMBER, 1)
                .with(TemporalAdjusters.dayOfWeekInMonth(4, DayOfWeek.THURSDAY));
    }

    // Test
    public static void main(String[] args) {
        LocalDateTime testDate = LocalDateTime.of(2025, 11, 27, 10, 0); // example Thanksgiving 2025
        System.out.println(isHoliday(testDate)); // true
    }
}
