package com.project.meli.demo.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.StringUtils.isBlank;

public final class DateUtils {

    private static final String FORMAT_YYYYMMDD = "yyyyMMdd";

    public static LocalDate parseStringToLocalDateTime(final String dateString) {
        if (isBlank(dateString)) {
            return null;
        }
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_YYYYMMDD);
        return LocalDate.parse(dateString, formatter);
    }
}
