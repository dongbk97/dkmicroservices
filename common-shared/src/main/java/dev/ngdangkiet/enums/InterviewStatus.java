package dev.ngdangkiet.enums;

import java.util.Arrays;

/**
 * @author ngdangkiet
 * @since 11/27/2023
 */

public enum InterviewStatus {

    SCHEDULED, COMPLETED, PENDING_DECISION;

    public static InterviewStatus of(String value) {
        return Arrays.stream(InterviewStatus.values())
                .filter(i -> i.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Interview status is invalid!"));
    }
}
