package dev.ngdangkiet.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ngdangkiet
 * @since 11/16/2023
 */

public class DateTimeUtil {

    private static final String FULL_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String FULL_DATE_PATTERN = "yyyy-MM-dd";
    private static final String LOCALTIME_PATTERN = "HH:mm:ss";

    public static String formatLocalDateTimeNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FULL_DATETIME_PATTERN);
        return LocalDateTime.now().format(formatter);
    }

    public static LocalDate convert2Localdate(String date) {
        return StringUtils.hasText(date) ? LocalDate.parse(date, DateTimeFormatter.ofPattern(FULL_DATE_PATTERN)) : null;
    }

    public static LocalTime convert2Localtime(String localTime) {
        return StringUtils.hasText(localTime) ? LocalTime.parse(localTime, DateTimeFormatter.ofPattern(LOCALTIME_PATTERN)) : null;
    }
}
