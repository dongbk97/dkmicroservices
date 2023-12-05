package dev.ngdangkiet.enums.attendance;

import java.util.Arrays;

/**
 * @author ngdangkiet
 * @since 12/2/2023
 */

public enum LeaveType {

    VACATION,
    SICK,
    BEREAVEMENT;

    public static LeaveType of(String value) {
        return Arrays.stream(LeaveType.values())
                .filter(lt -> lt.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Leave Type!"));
    }

    public static boolean isValidLeaveType(String value) {
        return Arrays.stream(LeaveType.values())
                .anyMatch(lt -> lt.name().equalsIgnoreCase(value));
    }
}
