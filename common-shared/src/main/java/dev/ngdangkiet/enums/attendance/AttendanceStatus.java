package dev.ngdangkiet.enums.attendance;

import java.util.Arrays;

/**
 * @author ngdangkiet
 * @since 12/2/2023
 */

public enum AttendanceStatus {

    PRESENT,
    ABSENT,
    LATE,
    EARLY_DEPARTURE,
    ON_LEAVE,
    HALF_DAY,
    OVERTIME;

    public static AttendanceStatus of(String value) {
        return Arrays.stream(AttendanceStatus.values())
                .filter(as -> as.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Attendance Status!"));
    }
}
