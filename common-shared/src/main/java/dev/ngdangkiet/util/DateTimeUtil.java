package dev.ngdangkiet.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ngdangkiet
 * @since 11/16/2023
 */

public class DateTimeUtil {

    private static final String FULL_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String formatLocalDateTimeNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FULL_DATETIME_PATTERN);
        return LocalDateTime.now().format(formatter);
    }
}
