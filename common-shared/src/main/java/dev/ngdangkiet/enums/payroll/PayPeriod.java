package dev.ngdangkiet.enums.payroll;

import java.util.Arrays;

/**
 * @author ngdangkiet
 * @since 12/15/2023
 */

public enum PayPeriod {

    MONTHLY;

    public static PayPeriod of(String value) {
        return Arrays.stream(PayPeriod.values())
                .filter(i -> i.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Pay period is invalid!"));
    }
}
