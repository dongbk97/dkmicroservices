package dev.ngdangkiet.enums;

import java.util.Arrays;

/**
 * @author ngdangkiet
 * @since 11/27/2023
 */

public enum ApplicantStatus {

    SUBMITTED,
    UNDER_REVIEW,
    INTERVIEW_SCHEDULED,
    INTERVIEWED,
    OFFER_EXTENDED,
    OFFER_ACCEPTED,
    REJECTED;

    public static ApplicantStatus of(String value) {
        return Arrays.stream(ApplicantStatus.values())
                .filter(a -> a.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Applicant status is invalid!"));
    }

    public static boolean isValidApplicantStatus(String value) {
        return Arrays.stream(ApplicantStatus.values())
                .anyMatch(a -> a.name().equalsIgnoreCase(value));
    }
}
