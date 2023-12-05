package dev.ngdangkiet.enums.attendance;

import java.util.Arrays;

/**
 * @author ngdangkiet
 * @since 12/2/2023
 */

public enum LeaveRequestStatus {

    PENDING,
    APPROVED,
    REJECTED,
    CANCELED;

    public static LeaveRequestStatus of(String value) {
        return Arrays.stream(LeaveRequestStatus.values())
                .filter(lqs -> lqs.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Leave Request Status!"));
    }

    public static boolean isValidLeaveRequestStatus(String value) {
        return Arrays.stream(LeaveRequestStatus.values())
                .anyMatch(lqs -> lqs.name().equalsIgnoreCase(value));
    }
}
