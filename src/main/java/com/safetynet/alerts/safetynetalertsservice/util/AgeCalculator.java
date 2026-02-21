package com.safetynet.alerts.safetynetalertsservice.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public final class AgeCalculator {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static int calculate(String birthdate) {
        LocalDate date = LocalDate.parse(birthdate, FORMATTER);
        return Period.between(date, LocalDate.now()).getYears();
    }
}
