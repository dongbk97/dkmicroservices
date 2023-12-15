package dev.ngdangkiet.enums.attendance;

import java.util.Arrays;

/**
 * @author ngdangkiet
 * @since 12/2/2023
 */

public enum HolidayType {

    NATIONAL,
    INTERNATIONAL,
    INTERNAL;

    public static HolidayType of(String value) {
        return Arrays.stream(HolidayType.values())
                .filter(as -> as.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Holiday Type!"));
    }
}
