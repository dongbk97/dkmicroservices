package dev.ngdangkiet.enums;

import java.util.Arrays;

/**
 * @author ngdangkiet
 * @since 11/27/2023
 */

public enum JobPostingStatus {

    ALL, OPEN, CLOSED, PENDING, CANCELLED;

    public static JobPostingStatus of(String value) {
        return Arrays.stream(JobPostingStatus.values())
                .filter(jp -> jp.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Job posting status is invalid!"));
    }

    public static boolean isValidInput(String value) {
        return Arrays.stream(JobPostingStatus.values())
                .anyMatch(jp -> jp.name().equalsIgnoreCase(value));
    }
}
