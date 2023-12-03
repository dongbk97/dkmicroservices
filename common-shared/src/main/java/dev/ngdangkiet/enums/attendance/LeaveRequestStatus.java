package dev.ngdangkiet.enums.attendance;

import java.util.Arrays;

/**
 * @author ngdangkiet
 * @since 12/2/2023
 */

public enum LeaveRequestStatus {

    PENDING,
    APPROVED,
    REJECTED;

    public static LeaveRequestStatus of(String value) {
        return Arrays.stream(LeaveRequestStatus.values())
                .filter(lqs -> lqs.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Leave Request Status!"));
    }
}
