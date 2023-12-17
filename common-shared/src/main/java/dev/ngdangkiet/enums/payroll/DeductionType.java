package dev.ngdangkiet.enums.payroll;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author ngdangkiet
 * @since 12/16/2023
 */

@Getter
@AllArgsConstructor
public enum DeductionType {

    HEALTH_INSURANCE("HI"),
    ACCIDENT_INSURANCE("AI"),
    SOCIAL_INSURANCE("SI");

    private final String shortName;

    public static DeductionType of(String value) {
        return Arrays.stream(DeductionType.values())
                .filter(i -> i.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Deduction type is invalid!"));
    }
}
