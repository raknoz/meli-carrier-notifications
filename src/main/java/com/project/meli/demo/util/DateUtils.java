package com.project.meli.demo.util;

import com.project.meli.demo.exceptions.BadRequestException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.StringUtils.isBlank;

public final class DateUtils {

    private static final String FORMAT_YYYYMMDD = "yyyyMMdd";

    public static LocalDate parseStringToLocalDateTime(final String dateString) {
        if (isBlank(dateString)) {
            return null;
        }
        try {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_YYYYMMDD);
            return LocalDate.parse(dateString, formatter);
        } catch (Exception ex) {
            throw new BadRequestException("Parsing Date error, please check It!");
        }
    }
}
