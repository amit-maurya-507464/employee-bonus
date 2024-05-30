package com.bonus.app.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MyUtils {

    public static LocalDate parseDate(String dateString) {
        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("MMM-dd-yyyy"),
                DateTimeFormatter.ofPattern("MMM-dd-yyyy")
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        return null;
    }

}
